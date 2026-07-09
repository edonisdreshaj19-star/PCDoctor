package de.dreshaj.pcdoctorapi.controller;

import de.dreshaj.pcdoctorapi.dto.DiagnosticReportResponseDto;
import de.dreshaj.pcdoctorapi.service.DiagnosticReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices/{deviceId}/diagnostic-reports")
public class DiagnosticReportController {

    private final DiagnosticReportService diagnosticReportService;

    public DiagnosticReportController(DiagnosticReportService diagnosticReportService) {
        this.diagnosticReportService = diagnosticReportService;
    }

    @PostMapping("/generate")
    public DiagnosticReportResponseDto generateReport(@PathVariable Long deviceId) {
        return diagnosticReportService.generateReport(deviceId);
    }

    @GetMapping("/latest")
    public DiagnosticReportResponseDto getLatestReport(@PathVariable Long deviceId) {
        return diagnosticReportService.getLatestReport(deviceId);
    }

    @GetMapping("/history")
    public List<DiagnosticReportResponseDto> getReportHistory(@PathVariable Long deviceId) {
        return diagnosticReportService.getReportHistory(deviceId);
    }
}