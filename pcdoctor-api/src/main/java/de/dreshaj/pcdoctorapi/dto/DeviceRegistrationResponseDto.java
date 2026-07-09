package de.dreshaj.pcdoctorapi.dto;

import java.time.LocalDateTime;

public class DeviceRegistrationResponseDto {
    private Long id;
    private String deviceName;
    private String deviceToken;
    private String operatingSystem;
    private LocalDateTime createdAt;
    private LocalDateTime lastSeenAt;

    public DeviceRegistrationResponseDto(
            Long id,
            String deviceName,
            String deviceToken,
            String operatingSystem,
            LocalDateTime createdAt,
            LocalDateTime lastSeenAt
    ) {
        this.id = id;
        this.deviceName = deviceName;
        this.deviceToken = deviceToken;
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

    public String getDeviceToken() {
        return deviceToken;
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
