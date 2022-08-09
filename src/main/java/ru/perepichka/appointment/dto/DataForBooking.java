package ru.perepichka.appointment.dto;

import lombok.Getter;
import lombok.Setter;
import ru.perepichka.appointment.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class DataForBooking {
    private String customerId;
    private LocalDate onDate;
    private LocalTime onTime;
    private String serviceId;
    private Integer duration;
    private Appointment.Status status;
    private String appointmentId;
}
