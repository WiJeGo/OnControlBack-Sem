package com.oncontrol.oncontrolbackend.profiles.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleResponse {
    private Long doctorId;
    private String doctorName;
    private String specialty;
    private List<String> availableDays;
    private List<String> availableHours;
    private String location;
    private Boolean isAvailable;
}
