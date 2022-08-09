package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.exception.InvalidDateTimeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class PeriodDTO {

    String from;
    String till;

    @JsonIgnore
    public LocalDatePeriod getAsLocalDatePeriod(){
        LocalDatePeriod period = new LocalDatePeriod();
        period.setFrom(getLocalDate(from));
        period.setTill(getLocalDate(till));
        return period;
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
