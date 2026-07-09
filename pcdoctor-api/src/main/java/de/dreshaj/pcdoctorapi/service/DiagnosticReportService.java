package de.dreshaj.pcdoctorapi.service;

import de.dreshaj.pcdoctorapi.dto.DiagnosticReportResponseDto;
import de.dreshaj.pcdoctorapi.dto.SystemHealthResponseDto;
import de.dreshaj.pcdoctorapi.model.DeviceEntity;
import de.dreshaj.pcdoctorapi.model.DiagnosticReportEntity;
import de.dreshaj.pcdoctorapi.model.SystemStatsEntity;
import de.dreshaj.pcdoctorapi.repository.DeviceRepository;
import de.dreshaj.pcdoctorapi.repository.DiagnosticReportRepository;
import de.dreshaj.pcdoctorapi.repository.SystemStatsRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DiagnosticReportService {

    private final DiagnosticReportRepository diagnosticReportRepository;
    private final DeviceRepository deviceRepository;
    private final SystemStatsRepository systemStatsRepository;
    private final SystemHealthService systemHealthService;

    public DiagnosticReportService(
            DiagnosticReportRepository diagnosticReportRepository,
            DeviceRepository deviceRepository,
            SystemStatsRepository systemStatsRepository,
            SystemHealthService systemHealthService
    ) {
        this.diagnosticReportRepository = diagnosticReportRepository;
        this.deviceRepository = deviceRepository;
        this.systemStatsRepository = systemStatsRepository;
        this.systemHealthService = systemHealthService;
    }

    public DiagnosticReportResponseDto generateReport(Long deviceId) {
        DeviceEntity device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Device not found with ID: " + deviceId));

        SystemStatsEntity latestStats = systemStatsRepository.findTop1ByDeviceIdOrderByCreatedAtDesc(deviceId)
                .orElseThrow(() -> new IllegalStateException("No system stats found for device ID: " + deviceId));

        SystemHealthResponseDto health = systemHealthService.getLatestHealth(deviceId);

        DiagnosticReportEntity report = new DiagnosticReportEntity();
        report.setDevice(device);
        report.setSystemStats(latestStats);
        report.setHealthScore(health.getScore());
        report.setStatus(health.getStatus());
        report.setSummary(buildSummary(health));
        report.setDetectedIssues(joinLines(health.getReasons()));
        report.setRecommendations(joinLines(health.getRecommendations()));

        DiagnosticReportEntity savedReport = diagnosticReportRepository.save(report);

        return mapToDto(savedReport);
    }

    public DiagnosticReportResponseDto getLatestReport(Long deviceId) {
        DiagnosticReportEntity report = diagnosticReportRepository
                .findTop1ByDeviceIdOrderByCreatedAtDesc(deviceId)
                .orElseThrow(() -> new IllegalStateException("No diagnostic report found for device ID: " + deviceId));

        return mapToDto(report);
    }

    public List<DiagnosticReportResponseDto> getReportHistory(Long deviceId) {
        return diagnosticReportRepository.findTop20ByDeviceIdOrderByCreatedAtDesc(deviceId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private DiagnosticReportResponseDto mapToDto(DiagnosticReportEntity report) {
        Long deviceId = report.getDevice() != null ? report.getDevice().getId() : null;
        Long systemStatsId = report.getSystemStats() != null ? report.getSystemStats().getId() : null;

        return new DiagnosticReportResponseDto(
                report.getId(),
                deviceId,
                systemStatsId,
                report.getHealthScore(),
                report.getStatus(),
                report.getSummary(),
                splitLines(report.getDetectedIssues()),
                splitLines(report.getRecommendations()),
                report.getCreatedAt()
        );
    }

    private String buildSummary(SystemHealthResponseDto health) {
        return switch (health.getStatus()) {
            case "HEALTHY" -> "System performance looks healthy. No immediate performance problem was detected.";
            case "WARNING" -> "System performance warnings were detected. The device may feel slower under load.";
            case "CRITICAL" -> "Critical system condition detected. Immediate attention is recommended.";
            default -> "System diagnosis completed.";
        };
    }

    private String joinLines(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }

        return String.join("\n", values);
    }

    private List<String> splitLines(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }

        return Arrays.stream(value.split("\\R"))
                .filter(line -> !line.isBlank())
                .toList();
    }
}