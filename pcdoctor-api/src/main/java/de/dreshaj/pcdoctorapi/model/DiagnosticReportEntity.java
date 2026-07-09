package de.dreshaj.pcdoctorapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DiagnosticReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int healthScore;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String detectedIssues;

    @Column(columnDefinition = "TEXT")
    private String recommendations;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private DeviceEntity device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_stats_id")
    private SystemStatsEntity systemStats;

    public Long getId() {
        return id;
    }

    public int getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(int healthScore) {
        this.healthScore = healthScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetectedIssues() {
        return detectedIssues;
    }

    public void setDetectedIssues(String detectedIssues) {
        this.detectedIssues = detectedIssues;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }

    public SystemStatsEntity getSystemStats() {
        return systemStats;
    }

    public void setSystemStats(SystemStatsEntity systemStats) {
        this.systemStats = systemStats;
    }
}