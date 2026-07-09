package de.dreshaj.pcdoctorapi.repository;

import de.dreshaj.pcdoctorapi.model.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    Optional<DeviceEntity> findByDeviceToken(String deviceToken);
}