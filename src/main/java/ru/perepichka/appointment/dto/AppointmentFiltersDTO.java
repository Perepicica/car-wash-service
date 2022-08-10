package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.appointment.specification.AppointmentFilters;
import ru.perepichka.util.DateTimeParserUtil;

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
        if(date!=null) filters.setDate(DateTimeParserUtil.getLocalDate(date));
        if(time!=null) filters.setTime(DateTimeParserUtil.getLocalTime(time));
        return filters;
    }
}
