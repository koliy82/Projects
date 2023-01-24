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
    /// Логика взаимодействия для Page3.xaml
    /// </summary>
    public partial class Page3 : Page
    {
        public Page3()
        {
            InitializeComponent();
        }
        private void MenuItem_Click(object sender, RoutedEventArgs e)
        {
            NavigationService.Navigate(new Page1());
        }

        private void MenuItem_Click1(object sender, RoutedEventArgs e)
        {
            NavigationService.Navigate(new Page2());
        }

        private void MenuItem_Click2(object sender, RoutedEventArgs e)
        {
            //NavigationService.Navigate(new Page3());
        }

        private void Clear_Click(object sender, RoutedEventArgs e)
        {
            txt.Text = "0";
            lasttxt.Text = "";
            actiontxt.Text = "";
            StringConverterToHEXandOCTandDECandBINsystemiSchislenya();
        }

        private void hex_btn_Click(object sender, RoutedEventArgs e)
        {
            txt.Text = hex_txt.Text;
            hex_btn.IsEnabled = false;
            dec_btn.IsEnabled = true;
            oct_btn.IsEnabled = true;
            bin_btn.IsEnabled = true;
            A_btn.IsEnabled = true;
            B_btn.IsEnabled = true;
            C_btn.IsEnabled = true;
            D_btn.IsEnabled = true;
            E_btn.IsEnabled = true;
            F_btn.IsEnabled = true;
            b8.IsEnabled = true;
            b9.IsEnabled = true;
            b2.IsEnabled = true;
            b3.IsEnabled = true;
            b4.IsEnabled = true;
            b5.IsEnabled = true;
            b6.IsEnabled = true;
            b7.IsEnabled = true;
            plus_minus_btn.IsEnabled = true;
        }

        private void dec_btn_Click(object sender, RoutedEventArgs e)
        {
            txt.Text = dec_txt.Text;
            hex_btn.IsEnabled = true;
            dec_btn.IsEnabled = false;
            oct_btn.IsEnabled = true;
            bin_btn.IsEnabled = true;
            A_btn.IsEnabled = false;
            B_btn.IsEnabled = false;
            C_btn.IsEnabled = false;
            D_btn.IsEnabled = false;
            E_btn.IsEnabled = false;
            F_btn.IsEnabled = false;
            b8.IsEnabled = true;
            b9.IsEnabled = true;
            b2.IsEnabled = true;
            b3.IsEnabled = true;
            b4.IsEnabled = true;
            b5.IsEnabled = true;
            b6.IsEnabled = true;
            b7.IsEnabled = true;
            plus_minus_btn.IsEnabled = true;
        }

        private void oct_btn_Click(object sender, RoutedEventArgs e)
        {
            txt.Text = oct_txt.Text;
            hex_btn.IsEnabled = true;
            dec_btn.IsEnabled = true;
            oct_btn.IsEnabled = false;
            bin_btn.IsEnabled = true;
            A_btn.IsEnabled = false;
            B_btn.IsEnabled = false;
            C_btn.IsEnabled = false;
            D_btn.IsEnabled = false;
            E_btn.IsEnabled = false;
            F_btn.IsEnabled = false;
            b8.IsEnabled = false;
            b9.IsEnabled = false;
            b2.IsEnabled = true;
            b3.IsEnabled = true;
            b4.IsEnabled = true;
            b5.IsEnabled = true;
            b6.IsEnabled = true;
            b7.IsEnabled = true;
            plus_minus_btn.IsEnabled = false;
        }

        private void bin_btn_Click(object sender, RoutedEventArgs e)
        {
            txt.Text = bin_txt.Text;
            hex_btn.IsEnabled = true;
            dec_btn.IsEnabled = true;
            oct_btn.IsEnabled = true;
            bin_btn.IsEnabled = false;
            A_btn.IsEnabled = false;
            B_btn.IsEnabled = false;
            C_btn.IsEnabled = false;
            D_btn.IsEnabled = false;
            E_btn.IsEnabled = false;
            F_btn.IsEnabled = false;
            b8.IsEnabled = false;
            b9.IsEnabled = false;
            b2.IsEnabled = false;
            b3.IsEnabled = false;
            b4.IsEnabled = false;
            b5.IsEnabled = false;
            b6.IsEnabled = false;
            b7.IsEnabled = false;
            plus_minus_btn.IsEnabled = false;
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
            StringConverterToHEXandOCTandDECandBINsystemiSchislenya();
        }
        void StringConverterToHEXandOCTandDECandBINsystemiSchislenya()
        {
            if (dec_btn.IsEnabled == false)
            {
                dec_txt.Text = txt.Text;
                bin_txt.Text = Convert.ToString(Convert.ToInt64(txt.Text), 2);
                oct_txt.Text = Convert.ToString(Convert.ToInt64(txt.Text), 8);
                dec_txt.Text = Convert.ToString(Convert.ToInt64(txt.Text), 10);
                hex_txt.Text = Convert.ToString(Convert.ToInt64(txt.Text), 16).ToUpper();
            }
            if (hex_btn.IsEnabled == false)
            {
                hex_txt.Text = txt.Text;
                String dec_ch = Int64.Parse(txt.Text, System.Globalization.NumberStyles.HexNumber).ToString();
                dec_txt.Text = dec_ch;
                oct_txt.Text = Convert.ToString(Convert.ToInt64(dec_ch), 8);
                bin_txt.Text = Convert.ToString(Convert.ToInt64(dec_ch), 2);
            }
            if(oct_btn.IsEnabled == false)
            {
                oct_txt.Text = txt.Text;
                Int64 vosem = Convert.ToInt64(txt.Text, 8);
                bin_txt.Text = Convert.ToString(vosem, 2);
                dec_txt.Text = Convert.ToString(vosem, 10);
                hex_txt.Text = Convert.ToString(vosem, 16).ToUpper();
            }
            if (bin_btn.IsEnabled == false)
            {
                bin_txt.Text = txt.Text;
                Int64 dva = Convert.ToInt64(txt.Text, 2);
                oct_txt.Text = Convert.ToString(dva, 8);
                dec_txt.Text = Convert.ToString(dva, 10);
                hex_txt.Text = Convert.ToString(dva, 16).ToUpper();
            }
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
            StringConverterToHEXandOCTandDECandBINsystemiSchislenya();
        }
        private void del_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(del_btn);
        }

        private void umn_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(umn_btn);
        }

        private void min_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(min_btn);
        }

        private void plus_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(plus_btn);
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
            StringConverterToHEXandOCTandDECandBINsystemiSchislenya();
        }

        private void res_btn_Click(object sender, RoutedEventArgs e)
        {
            String res = "0";
            Int64 second = Int64.Parse(txt.Text, System.Globalization.NumberStyles.HexNumber);
            if (lasttxt.Text == "") lasttxt.Text = "0";     
            if (lasttxt.Text == "НА НОЛЬ ДЕЛИТЬ НЕЛЬЗЯ") lasttxt.Text = "0";
            Int64 first = Int64.Parse(lasttxt.Text, System.Globalization.NumberStyles.HexNumber);
            switch (actiontxt.Text)
            {
                case "÷":
                    if (second != 0)
                        res = ((first) / (second)).ToString();
                    else
                        res = "0";
                    break;
                case "+":
                    res = ((first) + (second)).ToString();
                    break;
                case "-":
                    res = ((first) - (second)).ToString();
                    break;
                case "✕":
                    if(bin_btn.IsEnabled == false)
                    {
                        Int64 first2 = Convert.ToInt64(Convert.ToString(Convert.ToInt64(lasttxt.Text), 10));
                        Int64 second2 = Convert.ToInt64(txt.Text,10);
                        res = ((first2) * (second2)).ToString();
                        res = Convert.ToString(Convert.ToInt64(res), 2);
                    }
                    if(dec_btn.IsEnabled == false)
                    {
                        Int64 first2 = Convert.ToInt64(Convert.ToString(Convert.ToInt64(lasttxt.Text), 10));
                        Int64 second2 = Convert.ToInt64(txt.Text, 10);
                        res = ((first2) * (second2)).ToString();
                    }
                    if (oct_btn.IsEnabled == false)
                    {
                        Int64 first2 = Convert.ToInt64(Convert.ToString(Convert.ToInt64(lasttxt.Text), 10));
                        Int64 second2 = Convert.ToInt64(txt.Text, 10);
                        res = ((first2) * (second2)).ToString();
                        res = Convert.ToString(Convert.ToInt64(res), 8);
                    }
                    if(hex_btn.IsEnabled == false)
                    {
                        res = ((first) * (second)).ToString();
                    }
                    break;
                case "√x":
                    res = (Math.Pow(first, 1d / second)).ToString();
                    //res = (Math.Sqrt((first) + (second))).ToString();
                    break;
                case "x²":
                    res = (Math.Pow(first, second)).ToString();
                    break;
                case "%":
                    res = ((first * second) / 100).ToString();
                    // (число * процент) / 100
                    break;
                case "1/x":
                    res = (second / first).ToString();
                    break;
                case "»":
                    res = ((int)first >> (int)second).ToString();
                    break;
                case "«":
                    res = ((int)first << (int)second).ToString();
                    break;
                default:
                    break;
            }
            lasttxt.Text = "";
            actiontxt.Text = "";
            txt.Text = res.ToString();
            StringConverterToHEXandOCTandDECandBINsystemiSchislenya();
        }
        private void A_btn_Click(object sender, RoutedEventArgs e)
        {
            numAdd(A_btn);
        }

        private void B_btn_Click(object sender, RoutedEventArgs e)
        {
            numAdd(B_btn);
        }

        private void C_btn_Click(object sender, RoutedEventArgs e)
        {
            numAdd(C_btn);
        }

        private void D_btn_Click(object sender, RoutedEventArgs e)
        {
            numAdd(D_btn);
        }

        private void E_btn_Click(object sender, RoutedEventArgs e)
        {
            numAdd(E_btn);
        }

        private void F_btn_Click(object sender, RoutedEventArgs e)
        {
            numAdd(F_btn);
        }

        private void b0_Click(object sender, RoutedEventArgs e)
        {
            numAdd(b0);
        }

        private void plus_minus_btn_Click(object sender, RoutedEventArgs e)
        {
            double second = Convert.ToDouble(txt.Text);
            txt.Text = (second - (second * 2)).ToString();
        }

        private void pi_btn_Click(object sender, RoutedEventArgs e)
        {
            txt.Text = "3,1415926535897932384626433832795";
        }

        private void e_btn_Click(object sender, RoutedEventArgs e)
        {
            txt.Text = "2,7182818284590452353602874713527";
        }

        private void kav_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(kav_btn);
        }

        private void kav2_btn_Click(object sender, RoutedEventArgs e)
        {
            actionAdd(kav2_btn);
        }
    }
}
