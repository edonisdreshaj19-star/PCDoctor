package de.dreshaj.pcdoctorapi.dto;

public class ProcessStatsDto {
    private String processName;
    private int processId;
    private double memoryUsageMb;

    public ProcessStatsDto() {
    }

    public ProcessStatsDto(String processName, int processId, double memoryUsageMb) {
        this.processName = processName;
        this.processId = processId;
        this.memoryUsageMb = memoryUsageMb;
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
}