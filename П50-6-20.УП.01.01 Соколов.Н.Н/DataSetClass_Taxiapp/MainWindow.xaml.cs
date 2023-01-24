using Microsoft.Win32;
using ModernWpf;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Security.Cryptography;
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
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Threading;

namespace DataSetClass_Taxiapp
{
    /// <summary>
    /// Логика взаимодействия для MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public static SQL session;
        public string BD, SERVER;
        Boolean quest = true;
        ThemeManager tm = ThemeManager.Current;
        RegistryKey registry = Registry.CurrentUser;
        public static DispatcherTimer waitTimer = new DispatcherTimer();
        public MainWindow()
        {
            InitializeComponent();

            //Guid guid = Guid.NewGuid();
            //regSet("key", guid.ToString());
            string key = regGet("key");
            if (checkLicence(key)) new LicencesWindow().ShowDialog();

            checkAppInstall();
            //timerStart();
        }

        public static bool checkLicence(string key)
        {
            string[] licences = (File.Exists("licences")) ? File.ReadAllLines("licences") : new string[] { };
            foreach (var l in licences)
            {
                if (key == Decrypt(l, "key123")) return false;
            }
            return true;
        }

        public void timerStart()
        {
            waitTimer.Interval = TimeSpan.FromSeconds(120);
            waitTimer.Tick += TimerMessage;  
            waitTimer.Start();
        }
        private void Window_MouseMove(object sender, MouseEventArgs e)
        {
            waitTimer.Stop();
            waitTimer.Start();
        }

        private void TimerMessage(object sender, EventArgs e)
        {
            ModernWpf.MessageBox.Show("Вы слишком долго ничего не делали.");
        }

        public void checkAppInstall()
        {
            try
            {
                if (Type.GetTypeFromProgID("Word.Application") == null) { ModernWpf.MessageBox.Show("У вас не установлен Microsoft Word, из-за чего некоторый функционал программы может быть недоступен и/или программа может работать некорректно."); }
                if (Type.GetTypeFromProgID("Excel.Application") == null) { ModernWpf.MessageBox.Show("У вас не установлен Microsoft Excel, из-за чего некоторый функционал программы может быть недоступен и/или программа может работать некорректно."); }
                if (!(Directory.Exists(@"C:\Program Files\Microsoft SQL Server"))) { ModernWpf.MessageBox.Show("У вас не установлен Microsoft SQL Server, установите его для продолжения работы с программой."); Application.Current.Shutdown(); };
            }
            catch (Exception e)
            {
                exMessage(e);
            }
        }

        private void closingRequest(object sender, System.ComponentModel.CancelEventArgs e)
        {
            if (!quest) return;
            messageWindow msgwindow = new messageWindow();
            msgwindow.ShowDialog();
            if (msgwindow.ex == true)
            {
                // RestoreBounds - Возвращает размер и расположение окна перед тем как оно было свернуто или развернуто.
                //Properties.Settings.Default.WindowPosition = this.RestoreBounds;
                // Сохранение настроек.
                //Properties.Settings.Default.Save();

                regSet("Left", Left.ToString());
                regSet("Top", Top.ToString());

                e.Cancel = false;
                Application.Current.Shutdown();
            }
            else e.Cancel = true;
        }

        private void AuthorizationWindow_Loaded(object sender, RoutedEventArgs e)
        {
            string temp = regGet("Left");
            if(temp != null && temp !="") Left = Convert.ToDouble(temp);
            temp = regGet("Top");
            if(temp != null && temp != "") Top = Convert.ToDouble(temp);
            //Left = Properties.Settings.Default.WindowPosition.Left;
            //Top = Properties.Settings.Default.WindowPosition.Top;

            SERVER = regGet("SERVER");
            BD = regGet("BD");
            if(regGet("Theme") == "Light") LightTheme();
            else DarkTheme();
            Login_tb.Text = regGet("Login");
            Password_tb.Password = regGet("Password");
            if (SERVER != null && BD != null)
            {
                Thread connectionThread = new Thread(connection);
                connectionThread.Start();
            }
            else
            {
                status_tb.Text = "Ошибка подключения";
                status_pr.IsActive = false;
                connection_grid.Visibility = Visibility.Visible;
            }
        }
        private  string regGet(string textload, string encryptKey = "key123")
        {
            RegistryKey key = registry.CreateSubKey("TaxiDBConfig");
            textload = key.GetValue(textload)?.ToString();
            return Decrypt(textload, encryptKey);
        }
        private void regSet(string keyname, string textsave, string encryptKey = "key123")
        {
            RegistryKey key = registry.CreateSubKey("TaxiDBConfig");
            textsave = Encrypt(textsave, encryptKey);
            key.SetValue(keyname, textsave);
        }

        public static string Encrypt(string ishText, string pass, string sol = "doberman", string cryptographicAlgorithm = "SHA1", int passIter = 2, string initVec = "a8doSuDitOz1hZe#", int keySize = 256)
        {
            if (string.IsNullOrEmpty(ishText)) return "";

            byte[] initVecB = Encoding.ASCII.GetBytes(initVec);
            byte[] solB = Encoding.ASCII.GetBytes(sol);
            byte[] ishTextB = Encoding.UTF8.GetBytes(ishText);

            PasswordDeriveBytes derivPass = new PasswordDeriveBytes(pass, solB, cryptographicAlgorithm, passIter);
            byte[] keyBytes = derivPass.GetBytes(keySize / 8);
            RijndaelManaged symmK = new RijndaelManaged();
            symmK.Mode = CipherMode.CBC;

            byte[] cipherTextBytes = null;

            using (ICryptoTransform encryptor = symmK.CreateEncryptor(keyBytes, initVecB))
            {
                using (MemoryStream memStream = new MemoryStream())
                {
                    using (CryptoStream cryptoStream = new CryptoStream(memStream, encryptor, CryptoStreamMode.Write))
                    {
                        cryptoStream.Write(ishTextB, 0, ishTextB.Length);
                        cryptoStream.FlushFinalBlock();
                        cipherTextBytes = memStream.ToArray();
                        memStream.Close();
                        cryptoStream.Close();
                    }
                }
            }
            symmK.Clear();
            return Convert.ToBase64String(cipherTextBytes);
        }

        public static string Decrypt(string ciphText, string pass, string sol = "doberman", string cryptographicAlgorithm = "SHA1", int passIter = 2, string initVec = "a8doSuDitOz1hZe#", int keySize = 256)
        {
            if (string.IsNullOrEmpty(ciphText)) return "";

            byte[] initVecB = Encoding.ASCII.GetBytes(initVec);
            byte[] solB = Encoding.ASCII.GetBytes(sol);
            byte[] cipherTextBytes = Convert.FromBase64String(ciphText);

            PasswordDeriveBytes derivPass = new PasswordDeriveBytes(pass, solB, cryptographicAlgorithm, passIter);
            byte[] keyBytes = derivPass.GetBytes(keySize / 8);

            RijndaelManaged symmK = new RijndaelManaged();
            symmK.Mode = CipherMode.CBC;

            byte[] plainTextBytes = new byte[cipherTextBytes.Length];
            int byteCount = 0;

            using (ICryptoTransform decryptor = symmK.CreateDecryptor(keyBytes, initVecB))
            {
                using (MemoryStream mSt = new MemoryStream(cipherTextBytes))
                {
                    using (CryptoStream cryptoStream = new CryptoStream(mSt, decryptor, CryptoStreamMode.Read))
                    {
                        byteCount = cryptoStream.Read(plainTextBytes, 0, plainTextBytes.Length);
                        mSt.Close();
                        cryptoStream.Close();
                    }
                }
            }
            symmK.Clear();
            return Encoding.UTF8.GetString(plainTextBytes, 0, byteCount);
        }

        private void Find_BD_Click(object sender, RoutedEventArgs e)
        {
            Find_bt.IsEnabled = false;
            status_tb.Text = "Поиск БД";
            status_pr.IsActive = true;
            string temp = Server_tb.Text;
            Thread findTr = new Thread(() =>
            {
                SQL master = new SQL(temp, "master");
                DataTable bds = master.selectTable("select name from sys.databases");
                bool bd = false;
                for (int i = 4; i < bds.Rows.Count; i++) if (bds.Rows[i][0].ToString() == "Taxi_DB") bd = true;
                Application.Current.Dispatcher.Invoke(new Action(() =>
                {
                    try
                    {
                        BD_cb.Items.Clear();
                        BD_cb.IsEnabled = true;
                        connection_bt.IsEnabled = true;
                        for (int i = 4; i < bds.Rows.Count; i++)
                        {
                            BD_cb.Items.Add(bds.Rows[i][0].ToString());
                        }
                        BD_cb.SelectedIndex = 0;
                        if (BD_cb.Items.Count > 0)
                        {
                            status_tb.Text = "Выберите БД";
                            if (!bd) { ModernWpf.MessageBox.Show("У вас не установлен БД для работы с программой, установите Taxi_BD.sql из папки с программой"); Application.Current.Shutdown(); };
                        }
                        else status_tb.Text = "Введён неправильный сервер";
                    }
                    catch (Exception ex)
                    {
                        exMessage(ex);
                    }
                    Find_bt.IsEnabled = true;
                    status_pr.IsActive = false;
                }));
            });
            findTr.Start();
        }

        private void connection_bt_Click(object sender, RoutedEventArgs e)
        {
            SERVER = Server_tb.Text;
            BD = BD_cb.SelectedItem.ToString();
            status_tb.Text = "Подключение к базе данных...";
            status_pr.IsActive = true;
            if (SelectedAuth_cb.SelectedIndex == 0) {
                Thread connectionThread = new Thread(connection);
                connectionThread.Start();
            }
            else{
                session = new SQL(SERVER, BD, SelectedAuth_Log.Text, SelectedAuth_Pass.Password);
                try
                {
                    session.sql.Open();
                    session.sql.Close();
                    new AdminWindow().Show();
                    quest = false;
                    this.Close();
                }
                catch (Exception ex) {
                    exMessage(ex);
                }
            }
        }

        private void change_bt_Click(object sender, RoutedEventArgs e)
        {
            Login_bt.IsEnabled = false;
            Login_tb.IsEnabled = false;
            status_tb.Text = "Измените базу данных.";
            Password_tb.IsEnabled = false;
            connection_grid.Visibility = Visibility.Visible;
            change_bt.Visibility = Visibility.Hidden;
        }

        private void Login_bt_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                DataTable Client = session.selectTable($@"select * from dbo.Client WHERE Client_Login = '{Login_tb.Text}' and Client_Password = '{Password_tb.Password}'");
                DataTable Driver = session.selectTable($@"select * from dbo.Driver WHERE Driver_Login = '{Login_tb.Text}' and Driver_Password = '{Password_tb.Password}'");
                
                if (Client.Rows.Count > 0)
                {
                    SendMailWindow sendMailWindow = new SendMailWindow(Client.Rows[0][2].ToString());
                    sendMailWindow.ShowDialog();
                    if (sendMailWindow.DialogResult == false) return;
                    else new ClientWindow(Client).Show();
                }
                else if (Driver.Rows.Count > 0)
                {
                    SendMailWindow sendMailWindow = new SendMailWindow(Driver.Rows[0][2].ToString());
                    sendMailWindow.ShowDialog();
                    if (sendMailWindow.DialogResult == false) return;
                    else new DriverWindow(Driver).Show();
                }
                else { ModernWpf.MessageBox.Show("Неверный Логин или/и Пароль"); return; }

                if (remember_checkb.IsChecked == true)
                {
                    regSet("Login", Login_tb.Text);
                    regSet("Password", Password_tb.Password);
                }
                else {
                    regSet("Login", "");
                    regSet("Password", "");
                }
                quest = false;
                this.Close();
            }
            catch (Exception ex)
            {
                exMessage(ex);
            }
        }

        private void SelectedAuth_cb_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if (SelectedAuth_Log == null || SelectedAuth_Pass == null) return;
            if(SelectedAuth_cb.SelectedIndex == 0)
            {
                SelectedAuth_Log.Visibility = Visibility.Hidden;
                SelectedAuth_Pass.Visibility = Visibility.Hidden;
            }
            else
            {
                SelectedAuth_Log.Visibility = Visibility.Visible;
                SelectedAuth_Pass.Visibility = Visibility.Visible;
            }
        }

        public void connection()
        {
            session = new SQL(SERVER,BD);
            try
            {
                session.sql.Open();
                DataTable schema = session.sql.GetSchema("Tables");
                Thread.Sleep(3000);
                Application.Current.Dispatcher.Invoke(new Action(() =>
                {
                    if (schema.Rows.Count != 19) ModernWpf.MessageBox.Show($"Количество таблиц в БД не совпадает ({schema.Rows.Count}/19), возможно БД установленна не корректно.");
                    status_pr.IsActive = false;
                    Login_bt.IsEnabled = true;
                    Login_tb.IsEnabled = true;
                    Password_tb.IsEnabled = true;
                    connection_grid.Visibility = Visibility.Hidden;
                    change_bt.Visibility = Visibility.Visible;
                    regSet("SERVER", SERVER);
                    regSet("BD", BD);
                    status_tb.Text = $"Подключено к {BD}, авторизуйтесь.";
                }));
            }
            catch (Exception)
            {
                Application.Current.Dispatcher.Invoke(new Action(() =>
                {
                    status_tb.Text = "Ошибка подключения";
                    status_pr.IsActive = false;
                    connection_grid.Visibility = Visibility.Visible;
                }));
            }
            finally
            {
                session.sql.Close();
            }
        }
        private void ToggleThemeButton_Click(object sender, RoutedEventArgs e)
        {
            if (tm.ActualApplicationTheme == ApplicationTheme.Light) DarkTheme();
            else LightTheme();
        }

        public void LightTheme()
        {
            tm.ApplicationTheme = ApplicationTheme.Light;
            Background = (SolidColorBrush)new BrushConverter().ConvertFromString("#FFFFFFFF");
            regSet("Theme", "Light");
        }

        public void DarkTheme()
        {
            tm.ApplicationTheme = ApplicationTheme.Dark;
            Background = (SolidColorBrush)new BrushConverter().ConvertFromString("#1F2023");
            regSet("Theme", "Dark");
        }
        public static void exMessage(Exception e)
        {
            Application.Current.Dispatcher.Invoke(new Action(() =>
            {
                ModernWpf.MessageBox.Show(e.Message);
            }));
            LogExcep(e);
        }

        private void FPSMonitor_Click(object sender, RoutedEventArgs e)
        {
            bool isWindowOpen = false;
            foreach (Window w in Application.Current.Windows)
            {
                if (w is FPSMonitorWindow)
                {
                    isWindowOpen = true;
                    w.Activate();
                }
            }

            if (!isWindowOpen)
            {
                new FPSMonitorWindow().Show(); ;
            }
        }

        public static void LogExcep(Exception e)
        {
            if (!File.Exists("ErrorLogs.txt")){ File.Create("ErrorLogs.txt").Close(); File.WriteAllText("ErrorLogs.txt", "Log file is initialized"); }
            string temp = File.ReadAllText("ErrorLogs.txt");
            temp += $"\n{DateTime.Now} {e.Message}";
            File.WriteAllText("ErrorLogs.txt", temp);
        }
    }
}
