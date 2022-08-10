package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.appointment.Appointment;
import ru.perepichka.exception.InvalidAppointmentStatusException;
import ru.perepichka.util.DateTimeParserUtil;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class PutAppointmentDTO {

    @NotEmpty
    private String onDate;
    @NotEmpty
    private String onTime;
    @NotEmpty
    private String serviceId;
    @NotEmpty
    private String status;

    @JsonIgnore
    public DataForBooking getAsDataForBooking(){
        DataForBooking data = new DataForBooking();
        data.setOnDate(DateTimeParserUtil.getLocalDate(onDate));
        data.setOnTime(DateTimeParserUtil.getLocalTime(onTime));
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
