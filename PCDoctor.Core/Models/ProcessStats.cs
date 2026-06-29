namespace PCDoctor.Core.Models;

public class ProcessStats
{
    public string ProcessName { get; set; } = string.Empty;
    public int ProcessId { get; set; }
    public double MemoryUsageMB { get; set; }
}