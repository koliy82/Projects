using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.IO;
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
    /// Логика взаимодействия для AdminWindow.xaml
    /// </summary>
    public partial class AdminWindow : Window
    {
        Boolean quest = false;
        SQL session = MainWindow.session;
        int selectedClientID, selectedDriverID, selectedOrderID, selectedFeedbackID, selectedStartPointID, selectedEndPointID;
        public AdminWindow()
        {
            InitializeComponent();
            Background = App.Current.MainWindow.Background;
        }
        private void KeyGen_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                string key = File.ReadAllText("licences");
                string newkeys = "";
                for (int i = 1; i < Convert.ToInt32(Count_tb.Text); i++)
                {
                    Guid guid = Guid.NewGuid();
                    key += MainWindow.Encrypt(guid.ToString(), "key123")+"\n";
                    newkeys += guid.ToString() + "\n";
                }
                File.WriteAllText("licences", key);
                File.WriteAllText("newkeys", newkeys);
            }catch (Exception ex)
            {
                MainWindow.exMessage(ex);
            }
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
        private void AdminWindow_Loaded(object sender, RoutedEventArgs e)
        {
            try
            {
                ClientDataGridFill();
                DriverDataGridFill();
                OrderDataGridFill();
                CommentDataGridFill();
                StartPointDataGridFill();
                EndPointDataGridFill();
            }
            catch (Exception ex)
            {
                MainWindow.exMessage(ex);
            }
        }

        public void ClientDataGridFill()
        {
            Client_dg.Columns.Clear();
            Client_dg.ItemsSource = session.selectTable($@"select [ID_Client] as 'ID',[Client_LastName] as 'Фамилия',[Client_Name] as 'Имя',[Client_MiddleName] as 'Отчество', [Client_Login] as 'Логин', [Client_Password] as 'Пароль', [Client_Passport] as 'Паспорт', [Client_Card_Number] as 'Номер карты',[Client_Card_Date] as 'Срок карты',[Client_Card_CVV] as 'CVV'  from [dbo].[Client] where [Client_Name] like '%{SearchClient_tb.Text}%' order by [Client_LastName] {Sort_cb.Text}").DefaultView;
            //Client_dg.Columns[0].Visibility = Visibility.Hidden;
        }

        private void Client_dg_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (!(Client_dg.SelectedItem is DataRowView)) return;
            DataRowView selected = Client_dg.SelectedItem as DataRowView;
            selectedClientID = (int)selected[0];
            ClientLastName_tb.Text = selected[1].ToString();
            ClientName_tb.Text = selected[2].ToString();
            ClientMiddleName_tb.Text = selected[3].ToString();
            ClientLogin_tb.Text = selected[4].ToString();
            ClientPass_tb.Text = selected[5].ToString();
            ClientPassport_tb.Text = selected[6].ToString();
            CardNumber_tb.Text = selected[7].ToString();
            CardDate_tb.Text = selected[8].ToString();
            CardCVV_tb.Text= selected[9].ToString();
        }

        private void Client_Save_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"update [dbo].[Client] set [Client_LastName]='{ClientLastName_tb.Text}', [Client_Name]='{ClientName_tb.Text}',[Client_MiddleName]='{ClientMiddleName_tb.Text}',[Client_Login]='{ClientLogin_tb.Text}',[Client_Password]='{ClientPass_tb.Text}',[Client_Passport]='{ClientPassport_tb.Text}',[Client_Card_Number]='{CardNumber_tb.Text}',[Client_Card_Date]='{CardDate_tb.Text}',[Client_Card_CVV]='{CardCVV_tb.Text}' where[ID_Client] = '{selectedClientID}'");
            ClientDataGridFill();
        }

        private void Client_Add_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"insert into [dbo].[Client] ([Client_LastName],[Client_Name],[Client_MiddleName],[Client_Passport],[Client_Card_Number],[Client_Card_Date],[Client_Card_CVV],[Client_Login],[Client_Password]) values('{ClientLastName_tb.Text}', '{ClientName_tb.Text}', '{ClientMiddleName_tb.Text}', '{ClientPassport_tb.Text}', '{CardNumber_tb.Text}', '{CardDate_tb.Text}', '{CardCVV_tb.Text}', '{ClientLogin_tb.Text}', '{ClientPass_tb.Text}')");
            ClientDataGridFill();
        }

        private void Client_Del_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"delete from [dbo].[Client] where [ID_Client]='{selectedClientID}'");
            ClientDataGridFill();
        }
        public void DriverDataGridFill()
        {
            Driver_dg.Columns.Clear();
            Driver_dg.ItemsSource = session.selectTable($@"select [ID_Driver] as 'ID', [Driver_LastName] as 'Фамилия',[Driver_Name] as 'Имя', [Driver_MiddleName] as 'Отчество', [Driver_Login] as 'Логин', [Driver_Password] as 'Пароль', [Driver_Exp] as 'Стаж (лет)', [Driver_BirthDate] as 'Дата рождения',[Driver_SendDate] as 'Дата выдачи', [Driver_EndDate] as 'Дата окончания', [Driver_FromSend] as 'Кем выдан',[Driver_Number] as 'Серия и номер',[Driver_Residence] as 'Место жительства', [Driver_Category] as 'Категория' from [dbo].[Driver] where [Driver_Name] like '%{SearchDriver_tb.Text}%'").DefaultView;
            //Driver_dg.Columns[0].Visibility = Visibility.Hidden;
        }
        private void Driver_dg_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (!(Driver_dg.SelectedItem is DataRowView)) return;
            DataRowView selected = Driver_dg.SelectedItem as DataRowView;
            selectedDriverID = (int)selected[0];
            DriverLastName_tb.Text = selected[1].ToString();
            DriverName_tb.Text = selected[2].ToString();
            DriverMiddleName_tb.Text = selected[3].ToString();
            DriverLogin_tb.Text = selected[4].ToString();
            DriverPass_tb.Text = selected[5].ToString();
            DriverExp_tb.Text = selected[6].ToString();
            DriverBirthDate_tb.Text = selected[7].ToString();
            DriverStartDate_tb.Text = selected[8].ToString();
            DriverEndDate_tb.Text = selected[9].ToString();
            Driverkv_tb.Text = selected[10].ToString();
            DriverNumber_tb.Text= selected[11].ToString();
            DriverMestoShitelstva_tb.Text = selected[12].ToString();
            DriverCategory_tb.Text = selected[13].ToString();
        }

        private void Driver_Save_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"update [dbo].[Driver] set [Driver_LastName] = '{DriverLastName_tb.Text}',[Driver_Name] = '{DriverName_tb.Text}',[Driver_MiddleName] = '{DriverMiddleName_tb.Text}',[Driver_Login] = '{DriverLogin_tb.Text}',[Driver_Password] = '{DriverPass_tb.Text}',[Driver_Exp] = '{DriverExp_tb.Text}',[Driver_BirthDate] = '{DriverBirthDate_tb.Text}',[Driver_SendDate] = '{DriverStartDate_tb.Text}',[Driver_EndDate] = '{DriverEndDate_tb.Text}',[Driver_FromSend] = '{Driverkv_tb.Text}',[Driver_Number] = '{DriverNumber_tb.Text}',[Driver_Residence] = '{DriverMestoShitelstva_tb.Text}',[Driver_Category] = '{DriverCategory_tb.Text}'");
            DriverDataGridFill();
        }

        private void Driver_Add_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"insert into [dbo].[Driver] ([Driver_LastName],[Driver_Name],[Driver_MiddleName],[Driver_Login],[Driver_Password],[Driver_Exp],[Driver_BirthDate],[Driver_SendDate],[Driver_EndDate],[Driver_FromSend],[Driver_Number],[Driver_Residence],[Driver_Category]) values('{DriverLastName_tb.Text}', '{DriverName_tb.Text}', '{DriverMiddleName_tb.Text}', '{DriverLogin_tb.Text}', '{DriverPass_tb.Text}', '{DriverExp_tb.Text}', '{DriverBirthDate_tb.Text}', '{DriverStartDate_tb.Text}', '{DriverEndDate_tb.Text}', '{Driverkv_tb.Text}', '{DriverNumber_tb.Text}', '{DriverMestoShitelstva_tb.Text}', '{DriverCategory_tb.Text}')");
            DriverDataGridFill();
        }

        private void Driver_Del_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"delete from [dbo].[Driver] where [ID_Driver]='{selectedDriverID}'");
            DriverDataGridFill();
        }
        private void dataGridBugFix(object sender, DataGridAutoGeneratingColumnEventArgs e)
        {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor)e.PropertyDescriptor;
            e.Column.Header = propertyDescriptor.DisplayName;
            if (propertyDescriptor.DisplayName == "ID") e.Column.Visibility = Visibility.Hidden;
            if (e.PropertyType == typeof(DateTime)) (e.Column as DataGridTextColumn).Binding.StringFormat = "dd/MM/yyyy";
        }
        public void OrderDataGridFill()
        {
            Order_dg.Columns.Clear();
            Order_dg.ItemsSource = session.selectTable($@"select [ID_Order] as 'ID' ,[Order_Date] as 'Дата заказа',[Order_Time] as 'Время заказа',[Order_Number] as 'Номер заказа',[Order_Status] as 'Статус заказа', [Category_ID] as 'Категория', [Client_ID] as 'Клиент', [Driver_ID] as 'Водитель' from [dbo].[Order] inner join [dbo].[Category] on [Category_ID] = [ID_Category] inner join [dbo].[Client] on [Client_ID] = [ID_Client] inner join [dbo].[Driver] on [Driver_ID] = [ID_Driver] where [Order_Status] like '%{SearchOrder_tb.Text}%'").DefaultView;
        }
        private void Order_dg_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (!(Order_dg.SelectedItem is DataRowView)) return;
            DataRowView selected = Order_dg.SelectedItem as DataRowView;
            selectedOrderID = (int)selected[0];
            OrderDate_tb.Text = selected[1].ToString();
            OrderTime_tb.Text = selected[2].ToString();
            OrderNumber_tb.Text = selected[3].ToString();
            OrderStatus_tb.Text = selected[4].ToString();
            Category_tb.Text = selected[5].ToString();
            Client_tb.Text = selected[6].ToString();
            Driver_tb.Text = selected[7].ToString();
        }

        private void Order_Save_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"update [dbo].[Order] set [Order_Date] = '{OrderDate_tb.Text}', [Order_Time] = '{OrderTime_tb.Text}', [Order_Number] = '{OrderNumber_tb.Text}', [Order_Status] = '{OrderStatus_tb.Text}',  [Category_ID] = '{Category_tb.Text}', [Client_ID] = ''{Client_tb.Text}, [Driver_ID] = '{Driver_tb.Text}' where [ID_Order] = '{selectedOrderID}'");
            OrderDataGridFill();
        }

        private void Order_Add_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"insert into [dbo].[Order] ([Order_Number],[Order_Status],[Category_ID],[Client_ID],[Driver_ID]) values('{OrderNumber_tb.Text}', '{OrderStatus_tb.Text}', '{Category_tb.Text}', '{Client_tb.Text}', '{Driver_tb.Text}')");
            OrderDataGridFill();
        }

        private void Order_Del_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"delete from [dbo].[Order] where[ID_Order] = {selectedOrderID}");
            OrderDataGridFill();
        }

        public void CommentDataGridFill()
        {
            Comments_dg.Columns.Clear();
            Comments_dg.ItemsSource = session.selectTable($@"select [ID_Feedback] as 'ID', [Feedback_Text] as 'Комментарий', [Feedback_Mark] as 'Оценка', [Order_ID] as [Заказ ID] from [dbo].[Feedback] where [Feedback_Text] like '%{SearchComments_tb.Text}%'").DefaultView;
        }

        private void Comments_dg_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (!(Comments_dg.SelectedItem is DataRowView)) return;
            DataRowView selected = Comments_dg.SelectedItem as DataRowView;
            selectedFeedbackID = (int)selected[0];
            CommentInputText_tb.Text = selected[1].ToString();
            CommentMark_tb.Text = selected[2].ToString();
            CommentOrderID_tb.Text = selected[3].ToString();
        }

        private void StartPoint_dg_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (!(StartPoint_dg.SelectedItem is DataRowView)) return;
            DataRowView selected = StartPoint_dg.SelectedItem as DataRowView;
            selectedStartPointID = (int)selected[0];
            StartPoint_tb.Text = selected[1].ToString();
            StartPointOrderID_tb.Text = selected[2].ToString();
        }

        public void StartPointDataGridFill()
        {
            StartPoint_dg.Columns.Clear();
            StartPoint_dg.ItemsSource = session.selectTable($@"select [ID_Start_Point] as 'ID', [Start_Point_Name] as 'Пункт Назначения', [Order_ID] as 'Заказ ID' from [dbo].[Start_Point] where [Start_Point_Name] like '%{SearchStartAdr_tb.Text}%'").DefaultView;
        }

        private void StartPoint_Save_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"update [dbo].[Start_Point] set [Start_Point_Name] = '{StartPoint_tb.Text}', [Order_ID] = '{StartPointOrderID_tb.Text}' where [ID_Start_Point] = {selectedStartPointID}");
            StartPointDataGridFill();
        }

        private void StartPoint_Add_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"insert into [dbo].[Start_Point] ([Start_Point_Name],[Order_ID]) values('{StartPoint_tb.Text}', {StartPointOrderID_tb.Text})");
            StartPointDataGridFill();
        }

        public void EndPointDataGridFill()
        {
            EndPoint_dg.Columns.Clear();
            EndPoint_dg.ItemsSource = session.selectTable($@"select [ID_End_Point] as 'ID', [End_Point_Name] as 'Пункт Назначения', [Order_ID] as 'Заказ ID' from [dbo].[End_Point] where [End_Point_Name] like '%{SearchEndAdr_tb.Text}%'").DefaultView;
        }

        private void EndPoint_dg_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (!(EndPoint_dg.SelectedItem is DataRowView)) return;
            DataRowView selected = EndPoint_dg.SelectedItem as DataRowView;
            selectedEndPointID = (int)selected[0];
            EndPoint_tb.Text = selected[1].ToString();
            EndPointOrderID_tb.Text = selected[2].ToString();
        }

        private void EndPoint_Save_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"update [dbo].[End_Point] set [End_Point_Name] = '{EndPoint_tb.Text}', [Order_ID] = '{EndPointOrderID_tb.Text}' where [ID_End_Point] = {selectedEndPointID}");
            EndPointDataGridFill();
        }

        private void EndPoint_Add_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"insert into [dbo].[End_Point] ([End_Point_Name],[Order_ID]) values('{EndPoint_tb.Text}', {EndPointOrderID_tb.Text})");
            EndPointDataGridFill();
        }

        private void SearchStartAdr_tb_TextChanged(object sender, TextChangedEventArgs e)
        {
            StartPointDataGridFill();
        }

        private void SearchEndAdr_tb_TextChanged(object sender, TextChangedEventArgs e)
        {
            EndPointDataGridFill();
        }

        private void SearchClient_tb_TextChanged(object sender, TextChangedEventArgs e)
        {
            ClientDataGridFill();
        }

        private void SearchDriver_tb_TextChanged(object sender, TextChangedEventArgs e)
        {
            DriverDataGridFill();
        }

        private void SearchOrder_tb_TextChanged(object sender, TextChangedEventArgs e)
        {
            OrderDataGridFill();
        }

        private void SearchComments_tb_TextChanged(object sender, TextChangedEventArgs e)
        {
            CommentDataGridFill();
        }

        private void Sort_cb_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            ClientDataGridFill();
        }

        private void EndPoint_Del_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"delete from [dbo].[End_Point] where[ID_End_Point] = {selectedEndPointID}");
            EndPointDataGridFill();
        }

        private void StartPoint_Del_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"delete from [dbo].[Start_Point] where[ID_Start_Point] = {selectedStartPointID}");
            StartPointDataGridFill();
        }

        private void Comment_Save_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"update [dbo].[Feedback] set [Feedback_Text] = '{CommentInputText_tb.Text}', [Feedback_Mark] = {CommentMark_tb.Text} where [ID_Feedback] = '{selectedFeedbackID}'");
            CommentDataGridFill();
        }

        private void Comment_Add_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"insert into [dbo].[Feedback] ([Feedback_Text],[Feedback_Mark],[Order_ID]) values('{CommentInputText_tb.Text}', {CommentMark_tb.Text}, {CommentOrderID_tb.Text})");
            CommentDataGridFill();
        }

        private void Comment_Del_bt_Click(object sender, RoutedEventArgs e)
        {
            session.action($"delete from [dbo].[Feedback] where [ID_Feedback] = {selectedFeedbackID}");
            CommentDataGridFill();
        }
    }
}
