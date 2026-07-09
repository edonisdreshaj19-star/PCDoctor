package de.dreshaj.pcdoctorapi.controller;

import de.dreshaj.pcdoctorapi.dto.DeviceRegisterDto;
import de.dreshaj.pcdoctorapi.dto.DeviceRegistrationResponseDto;
import de.dreshaj.pcdoctorapi.dto.DeviceResponseDto;
import de.dreshaj.pcdoctorapi.model.DeviceEntity;
import de.dreshaj.pcdoctorapi.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/register")
    public DeviceRegistrationResponseDto registerDevice(@RequestBody DeviceRegisterDto dto) {
        DeviceEntity device = deviceService.registerDevice(dto);

        return mapToRegistrationResponseDto(device);
    }

    @GetMapping
    public List<DeviceResponseDto> getAllDevices() {
        return deviceService.getAllDevices()
                .stream()
                .map(this::mapToDeviceResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    public DeviceResponseDto getDeviceById(@PathVariable Long id) {
        DeviceEntity device = deviceService.getDeviceById(id);

        return mapToDeviceResponseDto(device);
    }

    private DeviceResponseDto mapToDeviceResponseDto(DeviceEntity device) {
        return new DeviceResponseDto(
                device.getId(),
                device.getDeviceName(),
                device.getOperatingSystem(),
                device.getCreatedAt(),
                device.getLastSeenAt()
        );
    }

    private DeviceRegistrationResponseDto mapToRegistrationResponseDto(DeviceEntity device) {
        return new DeviceRegistrationResponseDto(
                device.getId(),
                device.getDeviceName(),
                device.getDeviceToken(),
                device.getOperatingSystem(),
                device.getCreatedAt(),
                device.getLastSeenAt()
        );
    }
}