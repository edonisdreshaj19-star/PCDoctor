using PCDoctor.Core.Models;
using PCDoctor.Core.Services;

namespace PCDoctor.UI.Services;

public class WindowService
{
    private readonly AppSettings settings;
    private readonly SettingsService settingsService;
    private readonly ApiService apiService;

    public WindowService(AppSettings settings, SettingsService settingsService, ApiService apiService)
    {
        this.settings = settings;
        this.settingsService = settingsService;
        this.apiService = apiService;
    }

    public void OpenSettingsWindow()
    {
        SettingsWindow settingsWindow = new(settings, settingsService, apiService);
        settingsWindow.ShowDialog();
    }
}