using System.Windows.Input;
using PCDoctor.Core.Models;
using PCDoctor.UI.Commands;
using PCDoctor.UI.Services;

namespace PCDoctor.UI.ViewModels;

public class MainViewModel : BaseViewModel
{
    private readonly AppSettings settings;
    private readonly MonitoringService monitoringService;

    private CancellationTokenSource? monitoringCancellationTokenSource;

    public DashboardViewModel Dashboard { get; }

    public ICommand OpenSettingsCommand { get; }

    public MainViewModel(
        AppSettings settings,
        MonitoringService monitoringService,
        DashboardFormatter formatter,
        WindowService windowService)
    {
        this.settings = settings;
        this.monitoringService = monitoringService;

        Dashboard = new DashboardViewModel(formatter);
        OpenSettingsCommand = new RelayCommand(windowService.OpenSettingsWindow);
    }

    public async void StartMonitoring()
    {
        if (monitoringCancellationTokenSource != null)
        {
            return;
        }

        monitoringCancellationTokenSource = new CancellationTokenSource();
        CancellationToken token = monitoringCancellationTokenSource.Token;

        try
        {
            while (!token.IsCancellationRequested)
            {
                var result = await monitoringService.GetMonitoringResultAsync();

                Dashboard.Update(result);

                await Task.Delay(settings.RefreshIntervalSeconds * 1000, token);
            }
        }
        catch (TaskCanceledException)
        {
            // Monitoring stopped
        }
        finally
        {
            monitoringCancellationTokenSource?.Dispose();
            monitoringCancellationTokenSource = null;
        }
    }

    public void StopMonitoring()
    {
        monitoringCancellationTokenSource?.Cancel();
    }
}