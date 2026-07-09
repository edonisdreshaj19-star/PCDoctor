using System.Windows;
using PCDoctor.Core.Models;
using PCDoctor.Core.Services;
using PCDoctor.UI.ViewModels;

namespace PCDoctor.UI;

public partial class SettingsWindow : Window
{
    public SettingsWindow(
        AppSettings settings,
        SettingsService settingsService,
        ApiService apiService)
    {
        InitializeComponent();

        SettingsViewModel viewModel = new(settings, settingsService, apiService);

        viewModel.CloseRequested += CloseWindow;
        viewModel.ConfirmationRequested += ShowConfirmation;
        viewModel.NotificationRequested += ShowNotification;

        DataContext = viewModel;
    }

    private void CloseWindow(bool? dialogResult)
    {
        DialogResult = dialogResult;
        Close();
    }

    private bool ShowConfirmation(string message, string title)
    {
        MessageBoxResult result = MessageBox.Show(
            this,
            message,
            title,
            MessageBoxButton.YesNo,
            MessageBoxImage.Warning);

        return result == MessageBoxResult.Yes;
    }

    private void ShowNotification(string message, string title)
    {
        MessageBox.Show(
            this,
            message,
            title,
            MessageBoxButton.OK,
            MessageBoxImage.Information);
    }
}