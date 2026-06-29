using System.Windows;
using PCDoctor.Core.Models;
using PCDoctor.Core.Monitoring;

namespace PCDoctor.Core
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        
        private readonly SystemMonitor monitor;
        public MainWindow()
        {
            InitializeComponent();
            
            monitor = new SystemMonitor();
            StartMonitoring();
        }

        private async void StartMonitoring()
        {
            while (true)
            {
                SystemStats stats = monitor.GetStats();

                Dispatcher.Invoke(() =>
                    {
                        CpuUsageText.Text = $"{stats.CpuUsage:F1}%";

                        MemoryUsageText.Text = $"{stats.UsedMemoryMB} MB / {stats.TotalMemoryMB:F0} MB";
                        
                        DiskListBox.Items.Clear();

                        foreach (DiskStats disk in stats.Disks)
                        {
                            DiskListBox.Items.Add($"{disk.DriveName} {disk.UsedSpaceGB:F1} GB / {disk.TotalSpaceGB} GB ({disk.UsagePercentage:F1}%");
                        }
                    }
                );
                await Task.Delay(1000);
            }
        }
    }
}