package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class PeriodDto {

    @NotNull
    private LocalDate from;

    @NotNull
    private LocalDate till;

    @JsonIgnore
    public LocalDatePeriod getAsLocalDatePeriod(){
        LocalDatePeriod period = new LocalDatePeriod();
        period.setFrom(from);
        period.setTill(till);
        return period;
    }
}
