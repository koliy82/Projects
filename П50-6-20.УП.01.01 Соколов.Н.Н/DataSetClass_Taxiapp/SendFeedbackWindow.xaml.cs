using System;
using System.Collections.Generic;
using System.Data;
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
    /// Логика взаимодействия для SendFeedbackWindow.xaml
    /// </summary>
    public partial class SendFeedbackWindow : Window
    {
        SQL session = MainWindow.session;
        Boolean quest = false;
        int selectedhistoryOrderID;
        public SendFeedbackWindow(int selectedhistoryOrderID)
        {
            InitializeComponent();
            this.selectedhistoryOrderID = selectedhistoryOrderID;
            Background = App.Current.MainWindow.Background;
        }
        private void closingRequest(object sender, System.ComponentModel.CancelEventArgs e)
        {
            if (quest) return;
            messageWindow msgwindow = new messageWindow();
            msgwindow.ShowDialog();
            if (msgwindow.ex == true)
            {
                e.Cancel = false;
                Close();
            }
            else e.Cancel = true;
        }

        private void Save_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                Thread thread = new Thread(() =>
                {
                    DataTable Feedbacks = session.selectTable($"select [ID_Feedback] as 'ID', [Feedback_Text] as 'Текст отзыва', [Feedback_Mark] as 'Оценка отзыва' from [dbo].[Feedback] where[Order_ID] = '{selectedhistoryOrderID}'");
                    Application.Current.Dispatcher.Invoke(new Action(() =>
                    {
                        if (Feedbacks.Rows.Count == 0)
                    {
                        session.action($"insert into [dbo].[Feedback] ([Feedback_Text],[Feedback_Mark],[Order_ID]) values('{Comment_tb.Text}', {Mark_cb.Text}, '{selectedhistoryOrderID}')");
                        ModernWpf.MessageBox.Show("Спасибо за отзыв!");
                     }
                    else
                    {
                        session.action($"update [dbo].[Feedback] set [Feedback_Text] = '{Comment_tb.Text}', [Feedback_Mark] = '{Mark_cb.Text}' where [Order_ID] = '{selectedhistoryOrderID}'");
                        ModernWpf.MessageBox.Show("Отзыв обновлён!");
                    }
                        quest = true;
                        this.Close();
                    }));
                });
                thread.Start();
            }
            catch (Exception ex)
            {
                MainWindow.exMessage(ex);
            }
        }
    }
}
