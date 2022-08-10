package ru.perepichka.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.appointment.dto.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/appointments")
public class AppointmentController {

    private final AppointmentServiceImpl appointmentServiceImpl;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GetAppointmentDto> getAppointment(@RequestBody @Valid AppointmentFiltersDto dto,
                                                  Pageable pageable) {
        return appointmentServiceImpl.getAppointments(dto.getAsAppointmentFilters(), pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GetAppointmentDto createAppointment(@RequestBody @Valid PostAppointmentDto dto) {
        return appointmentServiceImpl.createAppointment(dto.getAsDataForBooking());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public GetAppointmentDto updateAppointment(@PathVariable(name = "id") String id, @RequestBody @Valid PutAppointmentDto dto) {
        return appointmentServiceImpl.updateAppointment(id, dto.getAsDataForBooking());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/revenue")
    public float getRevenue(@RequestBody @Valid PeriodDto dto) {
        return appointmentServiceImpl.getRevenue(dto.getAsLocalDatePeriod());
    }

}
