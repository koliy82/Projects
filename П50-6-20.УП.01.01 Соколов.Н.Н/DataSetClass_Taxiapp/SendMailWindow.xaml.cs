using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Mail;
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
    /// Логика взаимодействия для SendMailWindow.xaml
    /// </summary>
    public partial class SendMailWindow : Window
    {
        Boolean quest = false;
        public int authcode;
        string name = "";
        public SendMailWindow(string name)
        {
            InitializeComponent();
            Background = App.Current.MainWindow.Background;
            this.name = name;
        }
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            quest = true;
            if (authcode.ToString() == authCode_tb.Text) DialogResult = true;
            else ModernWpf.MessageBox.Show("Код не верный");
        }
        private void SendMailWindow_Loaded(object sender, RoutedEventArgs e)
        {
            Thread thread = new Thread(() =>
            {
                authcode = 0;
                //authcode = spamSend(name, "rutopruter@gmail.com");
            });
            thread.Start();
        }
        public int spamSend(string name, string sendMail)
        {
            int authcode = new Random().Next(100000000, 999999999);
            try
            {
                MailAddress From = new MailAddress("taxipraktika@gmail.com", "Taxi");
                MailAddress To = new MailAddress(sendMail);

                MailMessage msg = new MailMessage(From, To);
                msg.Subject = "Код авторизации";
                msg.Body = $"{name}, ваш код авторизации в приложении: {authcode}";

                SmtpClient smtp = new SmtpClient();
                smtp.Host = "smtp.gmail.com";
                smtp.Port = 587;
                smtp.EnableSsl = true;
                smtp.DeliveryMethod = SmtpDeliveryMethod.Network;
                smtp.UseDefaultCredentials = false;
                smtp.Credentials = new NetworkCredential(From.Address, "qwe123$$");

                smtp.Send(msg);
            }catch (Exception ex)
            {
                MainWindow.LogExcep(ex);
            }
            return authcode;
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            if (quest) return;
            messageWindow msgwindow = new messageWindow("Отменить авторизацию?");
            msgwindow.ShowDialog();
            if (msgwindow.ex == true)
            {
                e.Cancel = false;
                DialogResult = false;
            }
            else e.Cancel = true;
        }
    }
}
