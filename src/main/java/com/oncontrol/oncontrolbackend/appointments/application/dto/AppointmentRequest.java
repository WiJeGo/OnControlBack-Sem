package com.oncontrol.oncontrolbackend.appointments.application.dto;

import com.oncontrol.oncontrolbackend.appointments.domain.model.AppointmentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {
    
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    
    @NotNull(message = "Appointment date is required")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDate;
    
    @NotNull(message = "Duration is required")
    @Min(value = 15, message = "Duration must be at least 15 minutes")
    private Integer durationMinutes;
    
    @NotNull(message = "Appointment type is required")
    private AppointmentType type;
    
    private String location;
    
    private String notes;
    
    private String preparationInstructions;
    
    @Builder.Default
    private Boolean sendReminder = true;
}
