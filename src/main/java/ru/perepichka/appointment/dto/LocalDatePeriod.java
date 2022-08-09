package ru.perepichka.appointment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LocalDatePeriod {
    LocalDate from;
    LocalDate till;
}
