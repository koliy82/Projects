using Microsoft.Win32;
using System;
using System.Collections.Generic;
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
using System.Windows.Threading;

namespace DataSetClass_Taxiapp
{
    /// <summary>
    /// Логика взаимодействия для LicencesWindow.xaml
    /// </summary>
    public partial class LicencesWindow : Window
    {
        public static DispatcherTimer trialTimer = new DispatcherTimer();
        bool exitvopros = true;
        public LicencesWindow()
        {
            InitializeComponent();
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            if(exitvopros) Application.Current.Shutdown();
        }

        private void Trial_Click(object sender, RoutedEventArgs e)
        {
            exitvopros = false;
            trialTimer.Interval = TimeSpan.FromSeconds(500);
            trialTimer.Tick += TimerEnd;
            trialTimer.Start();
            this.Close();
        }

        private void TimerEnd(object sender, EventArgs e)
        {
            ModernWpf.MessageBox.Show("Время закочилось :3");
            Application.Current.Shutdown();
        }

        private void BuyLicense_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                string textkey = MainWindow.Encrypt(licence_tb.Text, "key123");
                if (MainWindow.checkLicence(textkey))
                {
                    RegistryKey key = Registry.CurrentUser.CreateSubKey("TaxiDBConfig");
                    key.SetValue("key", textkey);
                    ModernWpf.MessageBox.Show("Успешно активированно.");
                    exitvopros = false;
                    this.Close();
                }
                else
                {
                    ModernWpf.MessageBox.Show("Ключ неверный.");
                }
            }
            catch (Exception ex)
            {
                MainWindow.exMessage(ex);
            }

        }
    }
}
