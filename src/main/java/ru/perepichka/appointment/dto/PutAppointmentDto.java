package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.appointment.Appointment;
import ru.perepichka.exception.InvalidAppointmentStatusException;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class PutAppointmentDto {

    @FutureOrPresent
    private LocalDate onDate;
    @NotNull
    private LocalTime onTime;
    @NotEmpty
    private String serviceId;
    @NotEmpty
    private String status;

    @JsonIgnore
    public DataForBooking getAsDataForBooking(){
        DataForBooking data = new DataForBooking();
        data.setOnDate(onDate);
        data.setOnTime(onTime);
        data.setServiceId(serviceId);
        data.setStatus(getAsAppointmentStatus());
        return data;
    }

    @JsonIgnore
    public Appointment.Status getAsAppointmentStatus() {
        for (Appointment.Status st : Appointment.Status.values()) {
            if (st.name().equals(status.toUpperCase())) return st;
        }
        throw new InvalidAppointmentStatusException(status);
    }
}
