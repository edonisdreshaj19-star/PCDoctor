namespace PCDoctor.Core.Models;

public class AppSettings
{
    public string ApiBaseUrl { get; set; } = "http://localhost:8080";

    public int RefreshIntervalSeconds { get; set; } = 1;

    public int ApiSendIntervalSeconds { get; set; } = 10;

    public double CpuWarningThreshold { get; set; } = 85;

    public double MemoryWarningThreshold { get; set; } = 85;
}