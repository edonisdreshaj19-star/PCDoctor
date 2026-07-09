package de.dreshaj.pcdoctorapi.dto;

public class SystemStatsDto {
    private double cpuUsage;
    private double usedMemoryMb;
    private double totalMemoryMb;
    private double usedDiskGb;
    private double totalDiskGb;

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
}