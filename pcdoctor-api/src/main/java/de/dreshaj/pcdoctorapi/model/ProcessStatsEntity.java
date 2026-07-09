package de.dreshaj.pcdoctorapi.model;

import jakarta.persistence.*;

@Entity
public class ProcessStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String processName;
    private int processId;
    private double memoryUsageMb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_stats_id")
    private SystemStatsEntity systemStats;

    public Long getId() {
        return id;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public double getMemoryUsageMb() {
        return memoryUsageMb;
    }

    public void setMemoryUsageMb(double memoryUsageMb) {
        this.memoryUsageMb = memoryUsageMb;
    }

    public SystemStatsEntity getSystemStats() {
        return systemStats;
    }

    public void setSystemStats(SystemStatsEntity systemStats) {
        this.systemStats = systemStats;
    }
}