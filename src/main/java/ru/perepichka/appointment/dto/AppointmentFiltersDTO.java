package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.appointment.specification.AppointmentFilters;
import ru.perepichka.exception.InvalidDateTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class AppointmentFiltersDTO {
    private String boxId;
    private String date;
    private String time;

    @JsonIgnore
    public AppointmentFilters getAsAppointmentFilters(){
        AppointmentFilters filters = new AppointmentFilters();
        filters.setBoxId(boxId);
        if(date!=null) filters.setDate(getLocalDate(date));
        if(time!=null) filters.setTime(getLocalTime(time));
        return filters;
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
