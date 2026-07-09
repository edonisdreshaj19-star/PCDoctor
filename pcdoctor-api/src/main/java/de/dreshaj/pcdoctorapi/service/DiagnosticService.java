package de.dreshaj.pcdoctorapi.service;

import de.dreshaj.pcdoctorapi.dto.DiagnosticMessageDto;
import de.dreshaj.pcdoctorapi.dto.SystemStatsDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiagnosticService {

    public List<DiagnosticMessageDto> analyze(SystemStatsDto stats){
        List<DiagnosticMessageDto> messages = new ArrayList<>();

        double memoryUsagePercent =
                stats.getTotalMemoryMb() > 0
                ? (stats.getUsedMemoryMb() / stats.getTotalMemoryMb()) * 100
                : 0;

        if(stats.getCpuUsage() >= 85){
            messages.add(new DiagnosticMessageDto(
                    "WARNING",
                    "High CPU usage detected: " + String.format("%.1f", stats.getCpuUsage()) +"%"));
        }

        if (memoryUsagePercent >= 85) {
            messages.add(new DiagnosticMessageDto(
                    "WARNING",
                    "High memory usage detected: " + String.format("%.1f", memoryUsagePercent) + "%"
            ));
        }

        if (messages.isEmpty()) {
            messages.add(new DiagnosticMessageDto(
                    "OK",
                    "System performance looks normal."
            ));
        }

        return messages;
    }

}
