package ru.perepichka.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.perepichka.appointment.dto.DataForBooking;
import ru.perepichka.appointment.dto.GetAppointmentDto;
import ru.perepichka.appointment.dto.LocalDatePeriod;
import ru.perepichka.appointment.specification.AppointmentFilters;


public interface AppointmentService {

    Page<GetAppointmentDto> getAppointments(AppointmentFilters filters, Pageable pageable);

    GetAppointmentDto createAppointment(DataForBooking data);

    GetAppointmentDto updateAppointment(String id, DataForBooking data);

    float getRevenue(LocalDatePeriod period);
}
