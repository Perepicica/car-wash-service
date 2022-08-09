package ru.perepichka.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.appointment.dto.GetAppointmentDTO;
import ru.perepichka.appointment.dto.PostAppointmentDTO;

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

}
