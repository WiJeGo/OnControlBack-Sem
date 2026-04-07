package com.oncontrol.oncontrolbackend.appointments.domain.model;

public enum AppointmentStatus {
    SCHEDULED("Programada"),
    CONFIRMED("Confirmada"),
    IN_PROGRESS("En progreso"),
    COMPLETED("Completada"),
    CANCELLED("Cancelada"),
    NO_SHOW("No se presentó"),
    RESCHEDULED("Reprogramada");

    private final String displayName;

    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
