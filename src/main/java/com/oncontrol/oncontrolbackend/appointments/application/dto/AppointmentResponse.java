package com.oncontrol.oncontrolbackend.appointments.application.dto;

import com.oncontrol.oncontrolbackend.appointments.domain.model.AppointmentStatus;
import com.oncontrol.oncontrolbackend.appointments.domain.model.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private Long id;
    
    private Long doctorId;
    private String doctorName;
    
    private Long patientId;
    private String patientName;
    
    private LocalDateTime appointmentDate;
    private Integer durationMinutes;
    
    private AppointmentType type;
    private AppointmentStatus status;
    
    private String location;
    private String notes;
    private String preparationInstructions;
    
    private String followUpNotes;
    private String cancellationReason;
    
    private LocalDateTime completedAt;
    private LocalDateTime cancelledAt;
}
