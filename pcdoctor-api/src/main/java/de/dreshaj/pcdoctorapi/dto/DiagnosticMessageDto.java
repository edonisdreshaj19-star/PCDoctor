package de.dreshaj.pcdoctorapi.dto;

public class DiagnosticMessageDto {
    private String level;
    private String message;

    public DiagnosticMessageDto(String level, String message) {
        this.level = level;
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
}
