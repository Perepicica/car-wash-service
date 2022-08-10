package ru.perepichka.appointment.dto;

import lombok.Getter;
import lombok.Setter;
import ru.perepichka.box.dto.GetBoxDto;
import ru.perepichka.service.dto.GetServiceDto;
import ru.perepichka.user.dto.GetUserDto;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class GetAppointmentDto {
    private String id;
    private LocalDate date;
    private LocalTime startsAt;
    private LocalTime endsAt;
    private String status;
    private Float cost;
    private GetUserDto customer;
    private GetBoxDto box;
    private GetServiceDto service;
}
