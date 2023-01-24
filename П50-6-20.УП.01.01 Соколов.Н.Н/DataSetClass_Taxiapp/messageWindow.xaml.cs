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

namespace DataSetClass_Taxiapp
{
    /// <summary>
    /// Логика взаимодействия для messageWindow.xaml
    /// </summary>
    public partial class messageWindow : Window
    {
        public Boolean ex;
        public messageWindow(string messagetext = "Вы действительно хотите выйти?")
        {
            InitializeComponent();
            msg_tb.Text = messagetext;
            var temp = App.Current.MainWindow.Background;
            if(temp != null) Background = temp;
            else
            {
                foreach (Window w in App.Current.Windows)
                {
                    temp = w.Background;
                    if (temp != null) Background = temp;
                }
            }
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            ex = true;
            this.Close();
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            ex = false;
            this.Close();
        }
    }
}
