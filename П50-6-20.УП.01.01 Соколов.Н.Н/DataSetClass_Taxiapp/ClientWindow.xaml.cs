using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace DataSetClass_Taxiapp
{
    /// <summary>
    /// Логика взаимодействия для ClientWindow.xaml
    /// </summary>
    public partial class ClientWindow : Window
    {
        DataTable Client;
        SQL session = MainWindow.session;
        Boolean quest = false;
        public double price = 0;
        public int countAddPoint = 0;
        string ClientID;
        int selectedhistoryOrderID;
        public ClientWindow(DataTable Client)
        {
            InitializeComponent();
            Background = App.Current.MainWindow.Background;
            this.Client = Client;
        }

        private void ClientWindow_Loaded(object sender, RoutedEventArgs e)
        {
            updateHistory();
            updateFiles();
        }

        public void updateHistory()
        {
            HistoryClient_dg.Columns.Clear();
            ClientID = Client.Rows[0][0].ToString();
            HistoryClient_dg.ItemsSource = session.selectTable($@"select [ID_Order] as 'ID', [Order_Number] as '№ Заказа',[Order_Date] as 'Дата заказа',[Order_Time] as 'Время заказа', [Order_Status] as 'Статус заказа' from [dbo].[Order] where [Client_ID] = '{ClientID}' AND [Order_Number] like '%{SearchHistory_tb.Text}%'").DefaultView;
        }

        private void dataGridBugFix(object sender, DataGridAutoGeneratingColumnEventArgs e)
        {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor)e.PropertyDescriptor;
            e.Column.Header = propertyDescriptor.DisplayName;
            if (propertyDescriptor.DisplayName == "ID") e.Column.Visibility = Visibility.Hidden;
            if (e.PropertyType == typeof(DateTime)) (e.Column as DataGridTextColumn).Binding.StringFormat = "dd/MM/yyyy";
        }

        private void closingRequest(object sender, System.ComponentModel.CancelEventArgs e)
        {
            if (quest) return;
            messageWindow msgwindow = new messageWindow();
            msgwindow.ShowDialog();
            if (msgwindow.ex == true)
            {
                e.Cancel = false;
                Application.Current.Shutdown();
            }
            else e.Cancel = true;
        }

        public void CalculatePrice()
        {
            Thread thread = new Thread(() =>
            {
                Application.Current.Dispatcher.Invoke(new Action(() =>
                {
                    if (PassagerCount_cb == null || PassagerCount_cb.Text == "") return;
                    int categoryPrice = 0;
                    if (Category_cb.SelectedIndex == 0) categoryPrice = 170;
                    if (Category_cb.SelectedIndex == 1) categoryPrice = 370;
                    if (Category_cb.SelectedIndex == 2) categoryPrice = 570;
                    double addprice = countAddPoint * (0.3 * categoryPrice);
                    int temp = Convert.ToInt32(PassagerCount_cb.Text);
                    price = (categoryPrice + (temp * (categoryPrice * 0.2))) + addprice;
                    price_tb.Text = price.ToString() + "₽";
                }));
            });
            thread.Start();
        }
        private void Category_cb_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            CalculatePrice();
        }
        private void PassagerCount_cb_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            CalculatePrice();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            countAddPoint++;
            CalculatePrice();
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            try
            {
                newOrder_bt.IsEnabled = false;
                int categoryID = (Category_cb.Text == "Эконом") ? 1 : (Category_cb.Text == "Комфорт") ? 2 : 3;
                string StartPointAdress = StartPoint_tb.Text, EndPointAdress = EndPoint_tb.Text;
                Thread thread = new Thread(() =>
                {
                    Thread.CurrentThread.CurrentCulture = new System.Globalization.CultureInfo("en-US");
                    int lastID = Convert.ToInt32(session.selectTable("SELECT MAX(Order_Number) FROM[dbo].[Order]").Rows[0][0]) + 1;
                    session.action($"insert into [dbo].[Order] ([Order_Number],[Order_Status],[Category_ID],[Client_ID],[Driver_ID]) values('{lastID}', 'В пути', '{categoryID}', '{ClientID}', '1')");
                    if (StartPointAdress == "") { ModernWpf.MessageBox.Show("Поле ввода адреса отправления не должно быть пустое"); newOrder_bt.IsEnabled = true; return; }
                    if (EndPointAdress == "") { ModernWpf.MessageBox.Show("Поле ввода адреса назначения не должно быть пустое"); newOrder_bt.IsEnabled = true; return; }
                    if (StartPointAdress == EndPointAdress) { ModernWpf.MessageBox.Show("Изначальный и конечный пункты не должны совпадать"); newOrder_bt.IsEnabled = true; return; }
                    session.action($"insert into [dbo].[Start_Point] ([Start_Point_Name],[Order_ID]) values ('{StartPointAdress}','{lastID}')");
                    session.action($"insert into [dbo].[End_Point] ([End_Point_Name],[Order_ID]) values ('{EndPointAdress}','{lastID}')");
                    int lastReceiptID = Convert.ToInt32(session.selectTable("SELECT MAX(Receipt_Numm) FROM[dbo].[Receipt]").Rows[0][0]) + 1;
                    session.action($"insert into [dbo].[Receipt] ([Receipt_Numm],[Paid_Summ],[With_NDC],[Without_NDC],[Order_ID]) values('{lastReceiptID}', '{price}', '{price}', '{price - (price * 0.2)}', '{lastID}')");
                    Application.Current.Dispatcher.Invoke(new Action(() =>
                    {
                        updateHistory();
                        ModernWpf.MessageBox.Show($"Заказ №{lastID} успешно создан, ожидайте водителя");
                        newOrder_bt.IsEnabled = true;
                    }));
                });
                thread.Start();
            }
            catch (Exception ex)
            {
                MainWindow.exMessage(ex);
            }
        }

        private void toAuth_Click(object sender, RoutedEventArgs e)
        {
            new MainWindow().Show();
            quest = true;
            this.Close();
        }

        private void ReceiptExport_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                DataTable Receipt = session.selectTable($"select [Receipt_Numm] as 'Номер чека',[Receipt_Date] as 'Дата чека',[Receipt_Time] as 'Время чека',[Paid_Summ] as 'Внесённая сумма',[With_NDC] as 'С НДС',[Without_NDC] as 'БЕЗ НДС', [Order_ID] as 'Order_ID' from [dbo].[Receipt] inner join [dbo].[Order] on [Order_ID] = [ID_Order] where [Order_ID] = '{selectedhistoryOrderID}'");
                if(Receipt.Rows.Count == 0) { ModernWpf.MessageBox.Show("Чек ещё не создан"); return; }
                
                Microsoft.Office.Interop.Word.Application app = new Microsoft.Office.Interop.Word.Application();
                app.ShowAnimation = false;
                var doc = app.Documents.Add(Visible: false);
                var r = doc.Range();
                var t = doc.Tables.Add(r,16, 2);
                t.Borders.Enable = 0;

                //OOO "СИТИ-МОБИЛ"  |    https://city-mobil.ru
                //КАССОВЫЙ ЧЕК/ПРИХОД   |    21.02.22 15:25
                //  |   Чек N:24
                //СНО: ОСН  |    ИНН: 7728697453
                //ИТОГ  |    = 108
                //ВСЕГО ПОЛУЧЕНО:   |    108
                //БЕЗНАЛИЧНЫМИ:     |   108
                //ИТОГО без НДС:    |    108
                //САЙТ  |    ФНС: www.nalog.gov.ru
                //ЭЛ.АДР.ОТПРАВИТЕЛЯ    |    support@city-mobil.ru
                //РН ККТ: 0002472508022739  |    N ФН: 9960440301680011
                //N ФД: 164354  |    ФП: 2042698758
                //N АВТ.: 3820017912070

                //for (int i = 1; i <= 13; i++)
                //{
                //    t.Columns[2].Pa = 
                //}
                //t.Rows[2].Range.ParagraphFormat.Alignment = Microsoft.Office.Interop.Word.WdParagraphAlignment.wdAlignParagraphRight;
                for(int i = 1; i < 16; i++)
                {
                    t.Rows[i].Cells[2].Range.ParagraphFormat.Alignment = Microsoft.Office.Interop.Word.WdParagraphAlignment.wdAlignParagraphRight;
                }
                //t.Range.ParagraphFormat.Alignment = Microsoft.Office.Interop.Word.WdParagraphAlignment.wdAlignParagraphRight;

                t.Rows[1].Cells[1].Range.Text = "OOO \"СИТИ - МОБИЛ\"";           t.Rows[1].Cells[2].Range.Text = "https://city-mobil.ru";
                t.Rows[2].Cells[1].Range.Text = "Дата";                                                 t.Rows[2].Cells[2].Range.Text = $"{Receipt.Rows[0][1]}";//<----
                t.Rows[3].Cells[1].Range.Text = "Время";                                              t.Rows[3].Cells[2].Range.Text = $"{Receipt.Rows[0][2]}";//<----
                t.Rows[4].Cells[1].Range.Text = "КАССОВЫЙ ЧЕК/ПРИХОД";       t.Rows[4].Cells[2].Range.Text = $"Чек №{Receipt.Rows[0][0]}";//<----
                t.Rows[5].Cells[1].Range.Text = "СНО: ОСН";                                        t.Rows[5].Cells[2].Range.Text = "ИНН: 7728697453";
                t.Rows[6].Cells[1].Range.Text = "ИТОГ";                                                t.Rows[6].Cells[2].Range.Text = $"{Receipt.Rows[0][4]}";//<----
                t.Rows[7].Cells[1].Range.Text = "ВСЕГО ПОЛУЧЕНО:";                     t.Rows[7].Cells[2].Range.Text = $"{Receipt.Rows[0][3]}";//<----
                t.Rows[8].Cells[1].Range.Text = "БЕЗНАЛИЧНЫМИ:";                      t.Rows[8].Cells[2].Range.Text = $"{Receipt.Rows[0][3]}";//<----
                t.Rows[9].Cells[1].Range.Text = "ИТОГО без НДС:";                          t.Rows[9].Cells[2].Range.Text = $"{Receipt.Rows[0][5]}"; //<----
                t.Rows[10].Cells[1].Range.Text = "САЙТ";                                              t.Rows[10].Cells[2].Range.Text = "ФНС: www.nalog.gov.ru";
                t.Rows[11].Cells[1].Range.Text = "ЭЛ.АДР.ОТПРАВИТЕЛЯ";           t.Rows[11].Cells[2].Range.Text = "support@city-mobil.ru";
                t.Rows[12].Cells[1].Range.Text = "РН ККТ: 0002472508022739"; t.Rows[12].Cells[2].Range.Text = "N ФН: 9960440301680011";
                t.Rows[13].Cells[1].Range.Text = "N ФД: 164354";                              t.Rows[13].Cells[2].Range.Text = "ФП: 2042698758";
                t.Rows[14].Cells[1].Range.Text = "N АВТ.: 3820017912070";           t.Rows[14].Cells[2].Range.Text = " ";
                t.Rows[15].Cells[1].Range.Text = "Дата создания документа";     t.Rows[15].Cells[2].Range.Text = DateTime.Now.ToString("dd/MM/yyyy HH:mm");
                t.Rows[16].Cells[1].Range.Text = "Документ создан";                     t.Rows[16].Cells[2].Range.Text = $"{Client.Rows[0][2].ToString()+ " " + Client.Rows[0][3].ToString()}";


                doc.Save();
                doc.Close();
                app.Quit();

                if (!File.Exists("CreatedFiles.txt")) { File.Create("CreatedFiles.txt").Close();}
                string temp = File.ReadAllText("CreatedFiles.txt");
                temp += $"\n{DateTime.Now.ToString("dd/MM/yyyy HH:mm")}";
                File.WriteAllText("CreatedFiles.txt", temp);
                updateFiles();
            }
            catch(Exception ex)
            {
                MainWindow.exMessage(ex);
            }
        }

        private void HistoryClient_dg_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (!(HistoryClient_dg.SelectedItem is DataRowView)) return;
            DataRowView selected = HistoryClient_dg.SelectedItem as DataRowView;
            selectedhistoryOrderID = (int)selected[0];
        }

        private void sendFeedback_Click(object sender, RoutedEventArgs e)
        {
            new SendFeedbackWindow(selectedhistoryOrderID).ShowDialog();
        }

        private void SearchHistory_tb_TextChanged(object sender, TextChangedEventArgs e)
        {
            updateHistory();
        }

        void updateFiles()
        {
            FilesClient_dg.Columns.Clear();
            DataTable dt = new DataTable();
            if (!File.Exists("CreatedFiles.txt")) return;
            string temp = File.ReadAllText("CreatedFiles.txt");
            string[] temp2 = temp.Split('\n');
            dt.Columns.Add("Номер документа");
            dt.Columns.Add("Дата создания документа");
            for (int i = 1; i < temp2.Length-1; i++)
                dt.Rows.Add(i,temp2[i]);
            FilesClient_dg.ItemsSource = dt.DefaultView;
        }
    }
}
