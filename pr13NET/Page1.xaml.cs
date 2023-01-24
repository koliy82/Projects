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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace pr13NET
{
    /// <summary>
    /// Логика взаимодействия для Page1.xaml
    /// </summary>
    public partial class Page1 : Page
    {
        public Page1()
        {
            InitializeComponent();
        }
        void numAdd(Button b)
        {
            if (txt.Text == "0")
            {
                txt.Text = b.Content.ToString();
            }
            else if (txt.Text.Length < txt.MaxLength)
            {
                txt.Text += b.Content;
            }
        }
        void actionAdd(Button b)
        {
            if (lasttxt.Text == "" && actiontxt.Text == "")
            {
                lasttxt.Text = txt.Text;
                actiontxt.Text = b.Content.ToString();
                txt.Text = "0";
            }
            else
            {
                actiontxt.Text = b.Content.ToString();
            }
        }
        private void back_btn_Click(object sender, RoutedEventArgs e)
        {
            if (txt.Text.Length != 0)
            {
                txt.Text = txt.Text.Substring(0, txt.Text.Length - 1);
            }
            if (txt.Text.Length.Equals(0))
            {
                txt.Text = "0";
            }
        }
        private void b_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b0);
        }

        private void b1_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b1);
        }

        private void b2_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b2);
        }

        private void b3_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b3);
        }

        private void b4_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b4);
        }

        private void b5_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b5);
        }

        private void b6_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b6);
        }

        private void b7_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b7);
        }

        private void b8_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b8);
        }

        private void b9_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b9);
        }

        private void Clear_Click(object sender, RoutedEventArgs e)
        {
            txt.Text = "0";
            lasttxt.Text = "";
            actiontxt.Text = "";
        }

        private void proc_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(proc_btn);
        }
        private void del_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(del_btn);
        }
        private void umn_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(umn_btn);
        }
        private void res_btn_Click(object sender, RoutedEventArgs e)
        {
            String res = "0";
            double second = Convert.ToDouble(txt.Text);
            if (lasttxt.Text == "") lasttxt.Text = "0";
            if (lasttxt.Text == "НА НОЛЬ ДЕЛИТЬ НЕЛЬЗЯ") lasttxt.Text = "0";
            double first = Convert.ToDouble(lasttxt.Text);
            switch (actiontxt.Text)
            {
                case "÷":
                    if (second != 0)
                        res = ((first) / (second)).ToString();
                    else
                        res = "НА НОЛЬ ДЕЛИТЬ НЕЛЬЗЯ";
                    break;
                case "+":
                    res = ((first) + (second)).ToString();
                    break;
                case "-":
                    res = ((first) - (second)).ToString();
                    break;
                case "✕":
                    res = ((first) * (second)).ToString();
                    break;
                case "√x":
                    res = (Math.Pow(first, 1d / second)).ToString();
                    //res = (Math.Sqrt((first) + (second))).ToString();
                    break;
                case "x²":
                    res = (Math.Pow(first, second)).ToString();
                    break;
                case "%":
                    res = ((first*second)/100).ToString();
                    // (число * процент) / 100
                    break;
                case "1/x":
                    res = (second / first).ToString();
                    break;
                default:
                    break;
            }
            lasttxt.Text = res.ToString();
            txt.Text = "0";
        }

        private void min_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(min_btn);
        }

        private void plus_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(plus_btn);
        }

        private void cor_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(cor_btn);
        }

        private void plus_minus_btn_Click(object sender, RoutedEventArgs e)
        {
            double second = Convert.ToDouble(txt.Text);
            txt.Text = (second - (second * 2)).ToString();
        }

        private void zapyataya_btn_Click(object sender, RoutedEventArgs e)
        {
            bool iszapytaya = false;
            for (int i = 1; i < txt.Text.Length; i++)
            {
                if (txt.Text[i] == ',') iszapytaya = true;
            }
            if (!iszapytaya)
                txt.Text += ",";
        }
        private void MenuItem_Click(object sender, RoutedEventArgs e)
        {
            //NavigationService.Navigate(new Page1());
        }

        private void MenuItem_Click1(object sender, RoutedEventArgs e)
        {
            NavigationService.Navigate(new Page2());
        }

        private void MenuItem_Click2(object sender, RoutedEventArgs e)
        {
            NavigationService.Navigate(new Page3());
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(step_btn);
        }

        private void delx_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(delx_btn);
        }
    }
}
