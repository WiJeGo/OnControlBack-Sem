package com.oncontrol.oncontrolbackend.appointments.domain.model;

public enum AppointmentType {
    PRIMERA_CONSULTA("Primera consulta"),
    CONSULTA_SEGUIMIENTO("Consulta de seguimiento"),
    REVISION_TRATAMIENTO("Revisión de tratamiento"),
    REVISION_EXAMENES("Revisión de exámenes"),
    CONSULTA_URGENCIA("Consulta de urgencia"),
    CONSULTA_POST_OPERATORIA("Consulta post-operatoria"),
    SESION_QUIMIOTERAPIA("Sesión de quimioterapia"),
    EXAMENES_LABORATORIO("Exámenes de laboratorio"),
    CONSULTA_NUTRICION("Consulta nutricional"),
    CONSULTA_PSICOLOGICA("Consulta psicológica"),
    CONSULTA_DOLOR("Consulta de manejo del dolor"),
    CONSULTA_GENERAL("Consulta general"),
    OTRO("Otro");

    private final String displayName;

    AppointmentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
