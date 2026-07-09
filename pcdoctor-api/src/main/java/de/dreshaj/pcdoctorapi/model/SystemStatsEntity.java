package de.dreshaj.pcdoctorapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SystemStatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double cpuUsage;
    private double usedMemoryMb;
    private double totalMemoryMb;
    private double usedDiskGb;
    private double totalDiskGb;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private DeviceEntity device;

    @OneToMany(
            mappedBy = "systemStats",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProcessStatsEntity> topProcesses = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getUsedMemoryMb() {
        return usedMemoryMb;
    }

    public void setUsedMemoryMb(double usedMemoryMb) {
        this.usedMemoryMb = usedMemoryMb;
    }

    public double getTotalMemoryMb() {
        return totalMemoryMb;
    }

    public void setTotalMemoryMb(double totalMemoryMb) {
        this.totalMemoryMb = totalMemoryMb;
    }

    public double getUsedDiskGb() {
        return usedDiskGb;
    }

    public void setUsedDiskGb(double usedDiskGb) {
        this.usedDiskGb = usedDiskGb;
    }

    public double getTotalDiskGb() {
        return totalDiskGb;
    }

    public void setTotalDiskGb(double totalDiskGb) {
        this.totalDiskGb = totalDiskGb;
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

    public List<ProcessStatsEntity> getTopProcesses() {
        return topProcesses;
    }

    public void setTopProcesses(List<ProcessStatsEntity> topProcesses) {
        this.topProcesses = topProcesses == null ? new ArrayList<>() : topProcesses;
    }

    public void addTopProcess(ProcessStatsEntity processStats) {
        topProcesses.add(processStats);
        processStats.setSystemStats(this);
    }
}