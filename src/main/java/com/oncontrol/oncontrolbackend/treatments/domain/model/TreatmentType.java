package com.oncontrol.oncontrolbackend.treatments.domain.model;

public enum TreatmentType {
    CHEMOTHERAPY("Quimioterapia"),
    RADIOTHERAPY("Radioterapia"),
    IMMUNOTHERAPY("Inmunoterapia"),
    SURGERY("Cirugía"),
    HORMONE_THERAPY("Terapia Hormonal"),
    TARGETED_THERAPY("Terapia Dirigida"),
    STEM_CELL_TRANSPLANT("Trasplante de Células Madre");

    private final String displayName;

    TreatmentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

