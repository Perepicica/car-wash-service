package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.appointment.specification.AppointmentFilters;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentFiltersDto {
    private String boxId;
    private LocalDate date;
    private LocalTime time;

    @JsonIgnore
    public AppointmentFilters getAsAppointmentFilters() {
        AppointmentFilters filters = new AppointmentFilters();
        filters.setBoxId(boxId);
        filters.setDate(date);
        filters.setTime(time);
        return filters;
    }
}
