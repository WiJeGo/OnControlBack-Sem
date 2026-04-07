package com.oncontrol.oncontrolbackend.medicalrecords.domain.model;

public enum SeverityLevel {
    LOW("Bajo"),
    MEDIUM("Medio"),
    HIGH("Alto"),
    CRITICAL("Crítico");

    private final String displayName;

    SeverityLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

