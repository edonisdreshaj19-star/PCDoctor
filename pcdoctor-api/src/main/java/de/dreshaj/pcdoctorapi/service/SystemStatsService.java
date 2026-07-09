package de.dreshaj.pcdoctorapi.service;

import de.dreshaj.pcdoctorapi.dto.SystemStatsDto;
import de.dreshaj.pcdoctorapi.model.DeviceEntity;
import de.dreshaj.pcdoctorapi.model.SystemStatsEntity;
import de.dreshaj.pcdoctorapi.repository.DeviceRepository;
import de.dreshaj.pcdoctorapi.repository.SystemStatsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SystemStatsService {

    private final SystemStatsRepository systemStatsRepository;
    private final DeviceRepository deviceRepository;

    public SystemStatsService(SystemStatsRepository systemStatsRepository, DeviceRepository deviceRepository) {
        this.systemStatsRepository = systemStatsRepository;
        this.deviceRepository = deviceRepository;
    }

    public void saveStats(Long deviceId, SystemStatsDto stats) {
        DeviceEntity device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + deviceId));

        SystemStatsEntity entity = new SystemStatsEntity();
        entity.setDevice(device);
        entity.setCpuUsage(stats.getCpuUsage());
        entity.setUsedMemoryMb(stats.getUsedMemoryMb());
        entity.setTotalMemoryMb(stats.getTotalMemoryMb());

        device.setLastSeenAt(LocalDateTime.now());

        systemStatsRepository.save(entity);
        deviceRepository.save(device);
    }

    public SystemStatsEntity getLatestStats(Long deviceId) {
        return systemStatsRepository
                .findTop1ByDeviceIdOrderByCreatedAtDesc(deviceId)
                .orElseThrow(() -> new RuntimeException("No stats found for device with id: " + deviceId));
    }

    public List<SystemStatsEntity> getHistory(Long deviceId) {
        return systemStatsRepository.findTop50ByDeviceIdOrderByCreatedAtDesc(deviceId);
    }
}
