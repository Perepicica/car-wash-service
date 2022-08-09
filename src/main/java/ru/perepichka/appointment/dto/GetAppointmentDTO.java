package ru.perepichka.appointment.dto;

import lombok.Getter;
import lombok.Setter;
import ru.perepichka.box.dto.GetBoxDTO;
import ru.perepichka.service.dto.GetServiceDTO;
import ru.perepichka.user.dto.GetUserDTO;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class GetAppointmentDTO {
    private String id;
    private LocalDate date;
    private LocalTime startsAt;
    private LocalTime endsAt;
    private String status;
    private Float cost;
    private GetUserDTO customer;
    private GetBoxDTO box;
    private GetServiceDTO service;
}
