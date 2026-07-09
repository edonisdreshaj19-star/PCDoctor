using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace PCDoctor.UI.Models;

public class DiagnosticReportResponse
{
    [JsonPropertyName("id")]
    public long Id { get; set; }

    [JsonPropertyName("deviceId")]
    public long DeviceId { get; set; }

    [JsonPropertyName("systemStatsId")]
    public long SystemStatsId { get; set; }

    [JsonPropertyName("healthScore")]
    public int HealthScore { get; set; }

    [JsonPropertyName("status")]
    public string Status { get; set; } = string.Empty;

    [JsonPropertyName("summary")]
    public string Summary { get; set; } = string.Empty;

    [JsonPropertyName("detectedIssues")]
    public List<string> DetectedIssues { get; set; } = new();

    [JsonPropertyName("recommendations")]
    public List<string> Recommendations { get; set; } = new();

    [JsonPropertyName("createdAt")]
    public DateTime CreatedAt { get; set; }
}