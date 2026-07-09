package de.dreshaj.pcdoctorapi.service;

import de.dreshaj.pcdoctorapi.dto.DeviceRegisterDto;
import de.dreshaj.pcdoctorapi.model.DeviceEntity;
import de.dreshaj.pcdoctorapi.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceEntity registerDevice(DeviceRegisterDto dto) {
        DeviceEntity device = new DeviceEntity();
        device.setDeviceName(dto.getDeviceName());
        device.setDeviceToken(dto.getDeviceToken());
        device.setOperatingSystem(dto.getOperatingSystem());
        device.setLastSeenAt(LocalDateTime.now());

        return deviceRepository.save(device);
    }

    public List<DeviceEntity> getAllDevices() {
        return deviceRepository.findAll();
    }

    public DeviceEntity getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
    }
}
