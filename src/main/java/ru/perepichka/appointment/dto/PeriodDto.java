package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.util.DateTimeParserUtil;

@Getter
@Setter
public class PeriodDto {

    private String from;
    private String till;

    @JsonIgnore
    public LocalDatePeriod getAsLocalDatePeriod(){
        LocalDatePeriod period = new LocalDatePeriod();
        period.setFrom(DateTimeParserUtil.getLocalDate(from));
        period.setTill(DateTimeParserUtil.getLocalDate(till));
        return period;
    }
}
