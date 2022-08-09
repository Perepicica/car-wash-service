package ru.perepichka.appointment;

import ru.perepichka.appointment.dto.DataForBooking;
import ru.perepichka.appointment.dto.GetAppointmentDTO;
import ru.perepichka.appointment.dto.LocalDatePeriod;


public interface AppointmentService {
    GetAppointmentDTO createAppointment(DataForBooking data);

    GetAppointmentDTO updateAppointment(String id, DataForBooking data);

    float getRevenue(LocalDatePeriod period);
}
