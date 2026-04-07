package com.oncontrol.oncontrolbackend.appointments.application.dto;

import com.oncontrol.oncontrolbackend.appointments.domain.model.AppointmentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentRequest {

    @NotNull(message = "Appointment date is required")
    private LocalDateTime appointmentDate;

    private Integer durationMinutes; // Default: 30

    @NotNull(message = "Appointment type is required")
    private AppointmentType type;

    private String location;

    private String notes;

    private String preparationInstructions;

    private Boolean sendReminder; // Default: true
}

