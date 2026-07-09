package de.dreshaj.pcdoctorapi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SystemStatsResponseDto {
    private Long id;
    private double cpuUsage;
    private double usedMemoryMb;
    private double totalMemoryMb;
    private double usedDiskGb;
    private double totalDiskGb;
    private List<ProcessStatsDto> topProcesses;
    private LocalDateTime createdAt;

    public SystemStatsResponseDto(
            Long id,
            double cpuUsage,
            double usedMemoryMb,
            double totalMemoryMb,
            double usedDiskGb,
            double totalDiskGb,
            List<ProcessStatsDto> topProcesses,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.cpuUsage = cpuUsage;
        this.usedMemoryMb = usedMemoryMb;
        this.totalMemoryMb = totalMemoryMb;
        this.usedDiskGb = usedDiskGb;
        this.totalDiskGb = totalDiskGb;
        this.topProcesses = topProcesses;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public double getUsedMemoryMb() {
        return usedMemoryMb;
    }

    public double getTotalMemoryMb() {
        return totalMemoryMb;
    }

    public double getUsedDiskGb() {
        return usedDiskGb;
    }

    public double getTotalDiskGb() {
        return totalDiskGb;
    }

    public List<ProcessStatsDto> getTopProcesses() {
        return topProcesses;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}