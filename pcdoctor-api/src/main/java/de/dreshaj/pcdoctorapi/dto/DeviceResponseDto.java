package de.dreshaj.pcdoctorapi.dto;

import java.time.LocalDateTime;

public class DeviceResponseDto {
    private Long id;
    private String deviceName;
    private String operatingSystem;
    private LocalDateTime createdAt;
    private LocalDateTime lastSeenAt;

    public DeviceResponseDto(
            Long id,
            String deviceName,
            String operatingSystem,
            LocalDateTime createdAt,
            LocalDateTime lastSeenAt
    ) {
        this.id = id;
        this.deviceName = deviceName;
        this.operatingSystem = operatingSystem;
        this.createdAt = createdAt;
        this.lastSeenAt = lastSeenAt;
    }

    public Long getId() {
        return id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastSeenAt() {
        return lastSeenAt;
    }
}
