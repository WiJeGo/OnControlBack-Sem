package com.oncontrol.oncontrolbackend.symptoms.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SymptomSeverity {
    LEVE("Leve", "MILD"),
    MODERADA("Moderada", "MODERATE"),
    SEVERA("Severa", "SEVERE"),
    CRITICA("Crítica", "CRITICAL");

    private final String displayName;
    private final String englishValue;

    SymptomSeverity(String displayName, String englishValue) {
        this.displayName = displayName;
        this.englishValue = englishValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEnglishValue() {
        return englishValue;
    }

    @JsonValue
    public String toJson() {
        return this.name();
    }

    @JsonCreator
    public static SymptomSeverity fromString(String value) {
        if (value == null) {
            return null;
        }
        
        String upperValue = value.toUpperCase();
        
        // Try direct match first
        for (SymptomSeverity severity : SymptomSeverity.values()) {
            if (severity.name().equals(upperValue)) {
                return severity;
            }
        }
        
        // Try English value match
        for (SymptomSeverity severity : SymptomSeverity.values()) {
            if (severity.englishValue.equals(upperValue)) {
                return severity;
            }
        }
        
        // If not found, throw exception with helpful message
        throw new IllegalArgumentException(
            "Invalid severity value: " + value + 
            ". Accepted values: LEVE/MILD, MODERADA/MODERATE, SEVERA/SEVERE, CRITICA/CRITICAL"
        );
    }
}
