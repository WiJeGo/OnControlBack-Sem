package com.oncontrol.oncontrolbackend.treatments.domain.model;

public enum SessionStatus {
    SCHEDULED("Programada"),
    IN_PROGRESS("En Progreso"),
    COMPLETED("Completada"),
    CANCELLED("Cancelada"),
    RESCHEDULED("Reprogramada");

    private final String displayName;

    SessionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

