package ru.perepichka.appointment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class GetAppointmentForUserDTO {
    private String id;
    private LocalDate date;
    private LocalTime startsAt;
    private LocalTime endsAt;
    private String service;
    private Float cost;
    private String status;
}
