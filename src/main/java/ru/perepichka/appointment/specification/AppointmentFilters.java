package ru.perepichka.appointment.specification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentFilters {
    private String boxId;
    private LocalDate date;
    private LocalTime time;
}
