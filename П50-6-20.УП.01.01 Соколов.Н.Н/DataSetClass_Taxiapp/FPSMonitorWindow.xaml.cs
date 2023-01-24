using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net.NetworkInformation;
using System.Net.Sockets;
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
using System.Windows.Threading;
using Microsoft.VisualBasic.Devices;
using LiveCharts;
using LiveCharts.Wpf;

namespace DataSetClass_Taxiapp
{
    /// <summary>
    /// Логика взаимодействия для FPSMonitorWindow.xaml
    /// </summary>
    public partial class FPSMonitorWindow : Window
    {
        DispatcherTimer dispatcher = new DispatcherTimer();
        PerformanceCounter CPU = new PerformanceCounter("Processor", "% Processor Time", "_Total");
        PerformanceCounter RAM = new PerformanceCounter("Memory", "Available MBytes");
        ComputerInfo computerInfo = new ComputerInfo();
        public ChartValues<double> CPULine { get; set; }
        public ChartValues<double> RAMLine { get; set; }
        public double RAMMax { get; set; }
        public ChartValues<double> SpeedLine { get; set; }
        public ChartValues<double> ROMLine { get; set; }


        public FPSMonitorWindow()
        {
            InitializeComponent();

            CPULine = new ChartValues<double> { 0,0,0,0,0,0 };
            RAMLine = new ChartValues<double> { 0,0,0,0,0,0 };
            SpeedLine = new ChartValues<double> { 0,0,0,0,0,0 };
            ROMLine = new ChartValues<double> { 0,0,0,0,0, 0 };
            RAMMax = computerInfo.TotalPhysicalMemory / 1024 / 1024;
            Background = App.Current.MainWindow.Background;
            staticInfo();

            dispatcher.Interval = TimeSpan.FromSeconds(1);
            dispatcher.Tick += updateDispatcher;
            dispatcher.Start();

            DataContext = this;
        }
        void updateDispatcher(object sender, EventArgs e)
        {
            double cpuload = CPU.NextValue();
            double ramsave = RAM.NextValue();
            CPUSAGE_tb.Text = $"Загружен на {cpuload}%";
            RAM_tb.Text = $"Свободно {ramsave}/{RAMMax} MB";
            CPULine.RemoveAt(0);
            CPULine.Add(cpuload);
            RAMLine.RemoveAt(0);
            RAMLine.Add(ramsave);
            CheckInternetSpeed();

            ROM_tb.Text = "";
            foreach (var drive in DriveInfo.GetDrives())
            {
                ROM_tb.Text += $"Диск {drive.Name}\n";
                ROM_tb.Text += $"{drive.AvailableFreeSpace / 1024 / 1024}/{drive.TotalSize / 1024 / 1024} MB\n";
                if(drive.Name == @"C:\")
                {
                    ROMLine.RemoveAt(0);
                    ROMLine.Add(drive.AvailableFreeSpace / 1024 / 1024);
                }
            }

        }

        public void CheckInternetSpeed()
        {
            Thread thread = new Thread(new ThreadStart(() =>
            {
                try
                {
                System.Net.WebClient wc = new System.Net.WebClient();
                DateTime dt1 = DateTime.Now;
                byte[] data = wc.DownloadData("http://google.com");
                DateTime dt2 = DateTime.Now;
                Application.Current.Dispatcher.Invoke(new Action(() =>
                {
                    double curspeed = Math.Round((data.Length / 1024) / (dt2 - dt1).TotalSeconds, 2);
                    Network_tb.Text = $"{curspeed} kbps\n";
                    SpeedLine.RemoveAt(0);
                    SpeedLine.Add(curspeed);
                }));
                }
                catch (Exception) { }
            }
            ));
            thread.Start();
        }
        void staticInfo()
        {
            CPUCOUNT_tb.Text = $"Кол-во ядер {Environment.ProcessorCount}";
        }
    }
}
