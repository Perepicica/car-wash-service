package ru.perepichka.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.appointment.dto.GetAppointmentDTO;
import ru.perepichka.appointment.dto.PeriodDTO;
import ru.perepichka.appointment.dto.PostAppointmentDTO;
import ru.perepichka.appointment.dto.PutAppointmentDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/appointments")
public class AppointmentController {

    private final AppointmentServiceImpl appointmentServiceImpl;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GetAppointmentDTO createAppointment(@RequestBody @Valid PostAppointmentDTO dto) {
        return appointmentServiceImpl.createAppointment(dto.getAsDataForBooking());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public GetAppointmentDTO updateAppointment(@PathVariable(name = "id") String id, @RequestBody @Valid PutAppointmentDTO dto) {
        return appointmentServiceImpl.updateAppointment(id, dto.getAsDataForBooking());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/revenue")
    public float getRevenue(@RequestBody @Valid PeriodDTO dto){
        return appointmentServiceImpl.getRevenue(dto.getAsLocalDatePeriod());
    }

}
