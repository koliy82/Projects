using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;
using System.Text;
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
    /// Логика взаимодействия для DriverWindow.xaml
    /// </summary>
    public partial class DriverWindow : Window
    {
        Boolean quest = false;
        DataTable Driver;
        SQL session = MainWindow.session;
        string DriverID;
        int selectedDriverOrderID, newOrderID;
        public DriverWindow(DataTable Driver)
        {
            InitializeComponent();
            Background = App.Current.MainWindow.Background;
            this.Driver = Driver;
        }

        private void DriverWindow_Loaded(object sender, RoutedEventArgs e)
        {
            try
            {
                updateHistory();
                newOrderUpdate();
            }
            catch (Exception ex)
            {
                MainWindow.exMessage(ex);
            }
        }

        public void updateHistory()
        {
            HistoryDriver_dg.Columns.Clear();
            DriverID = Driver.Rows[0][0].ToString();
            HistoryDriver_dg.ItemsSource = session.selectTable($@"select [ID_Order] as 'ID', [Order_Number] as '№ Заказа',[Order_Date] as 'Дата заказа',[Order_Time] as 'Время заказа', [Order_Status] as 'Статус заказа' from [dbo].[Order] where[Driver_ID] = '{DriverID}'  AND [Order_Number] like '%{SearchHistory_tb.Text}%'").DefaultView;
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
        private void toAuth_Click(object sender, RoutedEventArgs e)
        {
            new MainWindow().Show();
            quest = true;
            this.Close();
        }
        private void dataGridBugFix(object sender, DataGridAutoGeneratingColumnEventArgs e)
        {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor)e.PropertyDescriptor;
            e.Column.Header = propertyDescriptor.DisplayName;
            if (propertyDescriptor.DisplayName == "ID") e.Column.Visibility = Visibility.Hidden;
            if (e.PropertyType == typeof(DateTime)) (e.Column as DataGridTextColumn).Binding.StringFormat = "dd/MM/yyyy";
        }

        private void HistoryDriver_dg_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (!(HistoryDriver_dg.SelectedItem is DataRowView)) return;
            DataRowView selected = HistoryDriver_dg.SelectedItem as DataRowView;
            selectedDriverOrderID = (int)selected[0];
        }

        public void newOrderUpdate()
        {
            newOrder_dg.Columns.Clear();
            newOrder_dg.ItemsSource = session.selectTable($@"select ID_Order as 'ID', [Order_Date] as 'Дата заказа',[Order_Time] as 'Время заказа',[Order_Number] as 'Номер заказа',[Order_Status] as 'Статус заказа' from [dbo].[Order] inner join [dbo].[Category] on [Category_ID] = [ID_Category] inner join [dbo].[Client] on [Client_ID] = [ID_Client] inner join [dbo].[Driver] on [Driver_ID] = [ID_Driver] where [Order_Status] not like 'Закрыт'").DefaultView;
        }

        private void newOrderApply_Click(object sender, RoutedEventArgs e)
        {
            session.action($"update [dbo].[Order] set [Order_Status] = 'Закрыт', [Driver_ID] = {DriverID} where [ID_Order] = '{newOrderID}'");
            newOrderUpdate();
            updateHistory();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            new SendFeedbackWindow(selectedDriverOrderID).ShowDialog();
        }

        private void SearchHistory_tb_TextChanged(object sender, TextChangedEventArgs e)
        {
            updateHistory();
        }

        private void newOrder_dg_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (!(newOrder_dg.SelectedItem is DataRowView)) return;
            DataRowView selected = newOrder_dg.SelectedItem as DataRowView;
            newOrderID = (int)selected[0];
        }
    }
}
