package de.dreshaj.pcdoctorapi.repository;

import de.dreshaj.pcdoctorapi.model.SystemStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemStatsRepository extends JpaRepository<SystemStatsEntity, Long> {

    List<SystemStatsEntity> findTop50ByOrderByCreatedAtDesc();

    List<SystemStatsEntity> findTop50ByDeviceIdOrderByCreatedAtDesc(Long deviceId);

    Optional<SystemStatsEntity> findTop1ByDeviceIdOrderByCreatedAtDesc(Long deviceId);
}
