package ru.perepichka.appointment.dto;

import lombok.Getter;
import lombok.Setter;
import ru.perepichka.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class DataForBooking {
    User customer;
    LocalDate onDate;
    LocalTime onTime;
    List<String> servicesId;
    Integer duration;
}
