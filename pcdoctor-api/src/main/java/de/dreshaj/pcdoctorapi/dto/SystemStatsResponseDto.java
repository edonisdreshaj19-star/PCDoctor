package de.dreshaj.pcdoctorapi.dto;

import java.time.LocalDateTime;

public class SystemStatsResponseDto {
    private Long id;
    private double cpuUsage;
    private double usedMemoryMb;
    private double totalMemoryMb;
    private double usedDiskGb;
    private double totalDiskGb;
    private LocalDateTime createdAt;

    public SystemStatsResponseDto(
            Long id,
            double cpuUsage,
            double usedMemoryMb,
            double totalMemoryMb,
            double usedDiskGb,
            double totalDiskGb,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.cpuUsage = cpuUsage;
        this.usedMemoryMb = usedMemoryMb;
        this.totalMemoryMb = totalMemoryMb;
        this.usedDiskGb = usedDiskGb;
        this.totalDiskGb = totalDiskGb;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}