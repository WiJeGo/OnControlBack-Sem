package com.oncontrol.oncontrolbackend.medicalrecords.domain.model;

public enum TreatmentStatus {
    ACTIVE("Activo"),
    COMPLETED("Completado"),
    SUSPENDED("Suspendido"),
    CANCELLED("Cancelado"),
    FOLLOW_UP("Seguimiento");

    private final String displayName;

    TreatmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
