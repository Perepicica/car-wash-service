package ru.perepichka.appointment;

import ru.perepichka.appointment.dto.DataForBooking;
import ru.perepichka.appointment.dto.GetAppointmentDTO;


public interface AppointmentService {
    GetAppointmentDTO createAppointment(DataForBooking data);
}
