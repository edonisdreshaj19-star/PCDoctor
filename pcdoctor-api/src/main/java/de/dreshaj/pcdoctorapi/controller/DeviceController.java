package de.dreshaj.pcdoctorapi.controller;

import de.dreshaj.pcdoctorapi.dto.DeviceRegisterDto;
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
    public DeviceEntity registerDevice(@RequestBody DeviceRegisterDto dto) {
        return deviceService.registerDevice(dto);
    }

    @GetMapping
    public List<DeviceEntity> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/{id}")
    public DeviceEntity getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }
}