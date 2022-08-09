package ru.perepichka.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.perepichka.appointment.dto.DataForBooking;
import ru.perepichka.appointment.dto.GetAppointmentDTO;
import ru.perepichka.appointment.dto.LocalDatePeriod;
import ru.perepichka.appointment.specification.AppointmentFilters;


public interface AppointmentService {

    Page<GetAppointmentDTO> getAppointments(AppointmentFilters filters,Pageable pageable);

    GetAppointmentDTO createAppointment(DataForBooking data);

    GetAppointmentDTO updateAppointment(String id, DataForBooking data);

    float getRevenue(LocalDatePeriod period);
}
