package de.dreshaj.pcdoctorapi.repository;

import de.dreshaj.pcdoctorapi.model.DiagnosticReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiagnosticReportRepository extends JpaRepository<DiagnosticReportEntity, Long> {

    Optional<DiagnosticReportEntity> findTop1ByDeviceIdOrderByCreatedAtDesc(Long deviceId);

    List<DiagnosticReportEntity> findTop20ByDeviceIdOrderByCreatedAtDesc(Long deviceId);
}