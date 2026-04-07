package com.oncontrol.oncontrolbackend.medicalrecords.domain.model;

public enum HistoryEntryType {
    DIAGNOSIS("Diagnóstico"),
    CONSULTATION("Consulta"),
    TREATMENT("Tratamiento"),
    TEST_RESULT("Resultado de Examen"),
    HOSPITALIZATION("Hospitalización"),
    SURGERY("Cirugía"),
    EMERGENCY("Emergencia"),
    FOLLOW_UP("Seguimiento"),
    REFERRAL("Referencia"),
    PRESCRIPTION("Prescripción"),
    VACCINATION("Vacunación"),
    ALLERGY("Alergia"),
    OTHER("Otro");

    private final String displayName;

    HistoryEntryType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

