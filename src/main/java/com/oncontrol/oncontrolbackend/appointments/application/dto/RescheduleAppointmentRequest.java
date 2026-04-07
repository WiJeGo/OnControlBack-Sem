package com.oncontrol.oncontrolbackend.appointments.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Request DTO for rescheduling an appointment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RescheduleAppointmentRequest {
    
    @NotNull(message = "New appointment date is required")
    private LocalDateTime newAppointmentDate;
    
    private String reason;
}

