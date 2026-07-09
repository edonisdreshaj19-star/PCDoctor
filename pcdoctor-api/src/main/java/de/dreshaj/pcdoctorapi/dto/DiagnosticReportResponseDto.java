package de.dreshaj.pcdoctorapi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DiagnosticReportResponseDto {

    private Long id;
    private Long deviceId;
    private Long systemStatsId;
    private int healthScore;
    private String status;
    private String summary;
    private List<String> detectedIssues;
    private List<String> recommendations;
    private LocalDateTime createdAt;

    public DiagnosticReportResponseDto(
            Long id,
            Long deviceId,
            Long systemStatsId,
            int healthScore,
            String status,
            String summary,
            List<String> detectedIssues,
            List<String> recommendations,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.deviceId = deviceId;
        this.systemStatsId = systemStatsId;
        this.healthScore = healthScore;
        this.status = status;
        this.summary = summary;
        this.detectedIssues = detectedIssues;
        this.recommendations = recommendations;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public Long getSystemStatsId() {
        return systemStatsId;
    }

    public int getHealthScore() {
        return healthScore;
    }

    public String getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public List<String> getDetectedIssues() {
        return detectedIssues;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}