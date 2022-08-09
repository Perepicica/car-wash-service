package ru.perepichka.appointment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class DataForBooking {
    String customerId;
    LocalDate onDate;
    LocalTime onTime;
    String serviceId;
    Integer duration;
}
