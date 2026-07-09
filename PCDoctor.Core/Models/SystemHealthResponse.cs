namespace PCDoctor.Core.Models;

public class SystemHealthResponse
{
    public int Score { get; set; }

    public string Status { get; set; } = "UNKNOWN";

    public List<string> Reasons { get; set; } = new();

    public List<string> Recommendations { get; set; } = new();
}