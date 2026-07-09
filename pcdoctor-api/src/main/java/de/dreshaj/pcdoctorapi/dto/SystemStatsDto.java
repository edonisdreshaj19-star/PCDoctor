package de.dreshaj.pcdoctorapi.dto;

public class SystemStatsDto {
    private double cpuUsage;
    private double usedMemoryMb;
    private double totalMemoryMb;

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
}
