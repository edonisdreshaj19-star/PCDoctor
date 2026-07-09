package de.dreshaj.pcdoctorapi.controller;

import de.dreshaj.pcdoctorapi.dto.DiagnosticMessageDto;
import de.dreshaj.pcdoctorapi.dto.SystemStatsDto;
import de.dreshaj.pcdoctorapi.model.SystemStatsEntity;
import de.dreshaj.pcdoctorapi.service.DiagnosticService;
import de.dreshaj.pcdoctorapi.service.SystemStatsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices/{deviceId}/system-stats")
public class SystemStatsController {

    private final SystemStatsService systemStatsService;
    private final DiagnosticService diagnosticService;

    public SystemStatsController(SystemStatsService systemStatsService, DiagnosticService diagnosticService) {
        this.systemStatsService = systemStatsService;
        this.diagnosticService = diagnosticService;
    }

    @PostMapping
    public String receiveStats(@PathVariable Long deviceId, @RequestBody SystemStatsDto stats) {
        systemStatsService.saveStats(deviceId, stats);
        return "System stats received for device with id: " + deviceId + ".";
    }

    @GetMapping("/latest")
    public SystemStatsEntity getLatestStats(@PathVariable Long deviceId) {
        return systemStatsService.getLatestStats(deviceId);
    }

    @GetMapping("/history")
    public List<SystemStatsEntity> getHistory(@PathVariable Long deviceId) {
        return systemStatsService.getHistory(deviceId);
    }

    @GetMapping("/diagnostics")
    public List<DiagnosticMessageDto> getDiagnostics(@PathVariable Long deviceId) {
        SystemStatsEntity latestStats = systemStatsService.getLatestStats(deviceId);

        SystemStatsDto dto = new SystemStatsDto();
        dto.setCpuUsage(latestStats.getCpuUsage());
        dto.setUsedMemoryMb(latestStats.getUsedMemoryMb());
        dto.setTotalMemoryMb(latestStats.getTotalMemoryMb());

        return diagnosticService.analyze(dto);
    }
}
