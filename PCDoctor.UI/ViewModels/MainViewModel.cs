using System.Collections.ObjectModel;
using System.Windows.Input;
using System.Windows.Media;
using PCDoctor.Core.Models;
using PCDoctor.Core.Services;
using PCDoctor.UI.Commands;
using PCDoctor.UI.Models;
using PCDoctor.UI.Services;

namespace PCDoctor.UI.ViewModels;

public class MainViewModel : BaseViewModel
{
    private readonly AppSettings settings;
    private readonly MonitoringService monitoringService;
    private readonly ApiService apiService;

    private CancellationTokenSource? monitoringCancellationTokenSource;

    private string selectedPage = "Overview";

    public DashboardViewModel Dashboard { get; }

    public SettingsViewModel Settings { get; }

    public ObservableCollection<DiagnosticReportListItem> DiagnosticReportRows { get; } = new();

    public ICommand GenerateDiagnosticReportCommand { get; }
    public ICommand RefreshDiagnosticReportsCommand { get; }
    public ICommand OpenReportsCommand { get; }
    public ICommand OpenDiagnosticsCommand { get; }

    private string diagnosticReportHistoryCountText = "0 reports";
    public string DiagnosticReportHistoryCountText
    {
        get => diagnosticReportHistoryCountText;
        set => SetProperty(ref diagnosticReportHistoryCountText, value);
    }

    private string latestDiagnosticReportTimeText = "-";
    public string LatestDiagnosticReportTimeText
    {
        get => latestDiagnosticReportTimeText;
        set => SetProperty(ref latestDiagnosticReportTimeText, value);
    }

    private string latestDiagnosticReportStatusText = "NO DATA";
    public string LatestDiagnosticReportStatusText
    {
        get => latestDiagnosticReportStatusText;
        set => SetProperty(ref latestDiagnosticReportStatusText, value);
    }

    private Brush latestDiagnosticReportStatusBrush = Brushes.Gray;
    public Brush LatestDiagnosticReportStatusBrush
    {
        get => latestDiagnosticReportStatusBrush;
        set => SetProperty(ref latestDiagnosticReportStatusBrush, value);
    }

    private string diagnosticReportHistoryButtonText = "Refresh History";
    public string DiagnosticReportHistoryButtonText
    {
        get => diagnosticReportHistoryButtonText;
        set => SetProperty(ref diagnosticReportHistoryButtonText, value);
    }

    public MainViewModel(
        AppSettings settings,
        SettingsService settingsService,
        MonitoringService monitoringService,
        ApiService apiService,
        DashboardFormatter formatter)
    {
        this.settings = settings;
        this.monitoringService = monitoringService;
        this.apiService = apiService;

        Dashboard = new DashboardViewModel(formatter);
        Settings = new SettingsViewModel(settings, settingsService, apiService);

        GenerateDiagnosticReportCommand = new RelayCommand(() => _ = GenerateDiagnosticReportAsync());
        RefreshDiagnosticReportsCommand = new RelayCommand(() => _ = RefreshDiagnosticReportsAsync());
        OpenReportsCommand = new RelayCommand(() => SelectPage("Reports"));
        OpenDiagnosticsCommand = new RelayCommand(() => SelectPage("Diagnostics"));

        SetEmptyDiagnosticReportHistory();
    }

    public string SelectedPage
    {
        get => selectedPage;
        private set
        {
            if (!SetProperty(ref selectedPage, value))
            {
                return;
            }

            OnPropertyChanged(nameof(PageTitle));
            OnPropertyChanged(nameof(PageSubtitle));

            OnPropertyChanged(nameof(IsOverviewSelected));
            OnPropertyChanged(nameof(IsDiagnosticsSelected));
            OnPropertyChanged(nameof(IsReportsSelected));
            OnPropertyChanged(nameof(IsHistorySelected));
            OnPropertyChanged(nameof(IsProcessesSelected));
            OnPropertyChanged(nameof(IsSettingsSelected));
        }
    }

    public string PageTitle => SelectedPage switch
    {
        "Overview" => "Overview",
        "Diagnostics" => "Diagnostics",
        "Reports" => "Reports",
        "History" => "History",
        "Processes" => "Processes",
        "Settings" => "Settings",
        _ => "PCDoctor"
    };

    public string PageSubtitle => SelectedPage switch
    {
        "Overview" => "Important system information at a glance.",
        "Diagnostics" => "Detailed diagnostic report and recommendations.",
        "Reports" => "Previously generated diagnostic reports.",
        "History" => "Previously collected system statistics.",
        "Processes" => "Running processes and memory-heavy applications.",
        "Settings" => "Application and API configuration.",
        _ => "System Monitoring & Diagnostics"
    };

    public bool IsOverviewSelected
    {
        get => SelectedPage == "Overview";
        set
        {
            if (value)
            {
                SelectPage("Overview");
            }
        }
    }

    public bool IsDiagnosticsSelected
    {
        get => SelectedPage == "Diagnostics";
        set
        {
            if (value)
            {
                SelectPage("Diagnostics");
            }
        }
    }

    public bool IsReportsSelected
    {
        get => SelectedPage == "Reports";
        set
        {
            if (value)
            {
                SelectPage("Reports");
            }
        }
    }

    public bool IsHistorySelected
    {
        get => SelectedPage == "History";
        set
        {
            if (value)
            {
                SelectPage("History");
            }
        }
    }

    public bool IsProcessesSelected
    {
        get => SelectedPage == "Processes";
        set
        {
            if (value)
            {
                SelectPage("Processes");
            }
        }
    }

    public bool IsSettingsSelected
    {
        get => SelectedPage == "Settings";
        set
        {
            if (value)
            {
                SelectPage("Settings");
            }
        }
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
            await RefreshDiagnosticReportsAsync();

            while (!token.IsCancellationRequested)
            {
                var result = await monitoringService.GetMonitoringResultAsync();

                Dashboard.Update(result);
                Settings.UpdateRuntimeStatus();

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

    private void SelectPage(string page)
    {
        SelectedPage = page;
    }

    private async Task GenerateDiagnosticReportAsync()
    {
        Dashboard.SetDiagnosticReportLoading(true);

        try
        {
            DiagnosticReportResponse? report = await apiService.GenerateDiagnosticReportAsync();

            if (report != null)
            {
                Dashboard.UpdateDiagnosticReport(report);
            }

            await LoadDiagnosticReportHistoryAsync(updateCurrentReport: false);

            Settings.UpdateRuntimeStatus();
        }
        finally
        {
            Dashboard.SetDiagnosticReportLoading(false);
        }
    }

    private async Task RefreshDiagnosticReportsAsync()
    {
        DiagnosticReportHistoryButtonText = "Refreshing...";

        try
        {
            await LoadDiagnosticReportHistoryAsync(updateCurrentReport: true);
            Settings.UpdateRuntimeStatus();
        }
        finally
        {
            DiagnosticReportHistoryButtonText = "Refresh History";
        }
    }

    private async Task LoadDiagnosticReportHistoryAsync(bool updateCurrentReport)
    {
        List<DiagnosticReportResponse> reports = await apiService.GetDiagnosticReportHistoryAsync();

        UpdateDiagnosticReportHistory(reports);

        if (!updateCurrentReport)
        {
            return;
        }

        DiagnosticReportResponse? latestReport = reports
            .OrderByDescending(report => report.CreatedAt)
            .FirstOrDefault();

        Dashboard.UpdateDiagnosticReport(latestReport);
    }

    private void UpdateDiagnosticReportHistory(List<DiagnosticReportResponse> reports)
    {
        DiagnosticReportRows.Clear();

        List<DiagnosticReportResponse> sortedReports = reports
            .OrderByDescending(report => report.CreatedAt)
            .ToList();

        if (sortedReports.Count == 0)
        {
            SetEmptyDiagnosticReportHistory();
            return;
        }

        DiagnosticReportHistoryCountText = $"{sortedReports.Count} reports";

        DiagnosticReportResponse latestReport = sortedReports[0];

        LatestDiagnosticReportTimeText = latestReport.CreatedAt.ToString("HH:mm:ss");
        LatestDiagnosticReportStatusText = latestReport.Status;
        LatestDiagnosticReportStatusBrush = GetReportStatusBrush(latestReport.Status);

        for (int index = 0; index < sortedReports.Count; index++)
        {
            DiagnosticReportRows.Add(DiagnosticReportListItem.Create(
                position: index + 1,
                report: sortedReports[index]));
        }
    }

    private void SetEmptyDiagnosticReportHistory()
    {
        DiagnosticReportRows.Clear();

        DiagnosticReportHistoryCountText = "0 reports";
        LatestDiagnosticReportTimeText = "-";
        LatestDiagnosticReportStatusText = "NO DATA";
        LatestDiagnosticReportStatusBrush = Brushes.Gray;

        DiagnosticReportRows.Add(DiagnosticReportListItem.CreateEmpty());
    }

    private static Brush GetReportStatusBrush(string status)
    {
        return status.ToUpperInvariant() switch
        {
            "HEALTHY" => Brushes.LightGreen,
            "WARNING" => Brushes.Gold,
            "CRITICAL" => Brushes.IndianRed,
            _ => Brushes.Gray
        };
    }
}

public class DiagnosticReportListItem
{
    public string PositionText { get; }
    public string CreatedAtText { get; }
    public string HealthScoreText { get; }
    public double HealthScoreBarValue { get; }
    public string StatusText { get; }
    public Brush StatusBrush { get; }
    public string SummaryText { get; }

    private DiagnosticReportListItem(
        string positionText,
        string createdAtText,
        string healthScoreText,
        double healthScoreBarValue,
        string statusText,
        Brush statusBrush,
        string summaryText)
    {
        PositionText = positionText;
        CreatedAtText = createdAtText;
        HealthScoreText = healthScoreText;
        HealthScoreBarValue = healthScoreBarValue;
        StatusText = statusText;
        StatusBrush = statusBrush;
        SummaryText = summaryText;
    }

    public static DiagnosticReportListItem Create(int position, DiagnosticReportResponse report)
    {
        return new DiagnosticReportListItem(
            positionText: $"#{position}",
            createdAtText: report.CreatedAt.ToString("HH:mm:ss"),
            healthScoreText: $"{report.HealthScore} / 100",
            healthScoreBarValue: Math.Clamp(report.HealthScore, 0, 100),
            statusText: report.Status,
            statusBrush: GetStatusBrush(report.Status),
            summaryText: string.IsNullOrWhiteSpace(report.Summary)
                ? "No summary available."
                : report.Summary);
    }

    public static DiagnosticReportListItem CreateEmpty()
    {
        return new DiagnosticReportListItem(
            positionText: "-",
            createdAtText: "-",
            healthScoreText: "-",
            healthScoreBarValue: 0,
            statusText: "NO DATA",
            statusBrush: Brushes.Gray,
            summaryText: "No diagnostic reports available yet. Run a diagnosis to create the first report.");
    }

    private static Brush GetStatusBrush(string status)
    {
        return status.ToUpperInvariant() switch
        {
            "HEALTHY" => Brushes.LightGreen,
            "WARNING" => Brushes.Gold,
            "CRITICAL" => Brushes.IndianRed,
            _ => Brushes.Gray
        };
    }
}