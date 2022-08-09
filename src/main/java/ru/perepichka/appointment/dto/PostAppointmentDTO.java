package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.exception.InvalidDateTimeException;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class PostAppointmentDTO {
    @NotEmpty
    private String customerId;
    @NotEmpty
    private String onDate;
    @NotEmpty
    private String onTime;
    @NotEmpty
    private String serviceId;

    @JsonIgnore
    public DataForBooking getAsDataForBooking(){
        DataForBooking data = new DataForBooking();

        data.setCustomerId(customerId);
        data.setOnDate(getLocalDate(onDate));
        data.setOnTime(getLocalTime(onTime));
        data.setServiceId(serviceId);
        data.setAppointmentId("0");

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

}
