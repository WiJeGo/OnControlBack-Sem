package com.oncontrol.oncontrolbackend.medicalrecords.domain.model;

public enum AllergyType {
    MEDICATION("Medicamento"),
    FOOD("Alimento"),
    ENVIRONMENTAL("Ambiental"),
    ANIMAL("Animal"),
    INSECT("Insecto"),
    CHEMICAL("Químico"),
    OTHER("Otro");

    private final String displayName;

    AllergyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

