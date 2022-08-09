package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.appointment.Appointment;
import ru.perepichka.exception.InvalidAppointmentStatusException;
import ru.perepichka.exception.InvalidDateTimeException;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        data.setOnDate(getLocalDate(onDate));
        data.setOnTime(getLocalTime(onTime));
        data.setServiceId(serviceId);
        data.setStatus(getAsAppointmentStatus());
        return data;
    }

    @JsonIgnore
    private LocalTime getLocalTime(String time){
        String format = "HH:mm";
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern(format));
        } catch (DateTimeParseException e){
            throw new InvalidDateTimeException(format);
        }
    }

    @JsonIgnore
    private LocalDate getLocalDate(String date){
        String format = "d.MM.yyyy";
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
        } catch (DateTimeParseException e){
            throw new InvalidDateTimeException(format);
        }
    }

    @JsonIgnore
    public Appointment.Status getAsAppointmentStatus() {
        for (Appointment.Status st : Appointment.Status.values()) {
            if (st.name().equals(status.toUpperCase())) return st;
        }
        throw new InvalidAppointmentStatusException(status);
    }
}
