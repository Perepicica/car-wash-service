package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class PostAppointmentDto {
    @NotEmpty
    private String customerId;
    @NotNull
    private LocalDate onDate;
    @NotNull
    private LocalTime onTime;
    @NotEmpty
    private String serviceId;

    @JsonIgnore
    public DataForBooking getAsDataForBooking(){
        DataForBooking data = new DataForBooking();

        data.setCustomerId(customerId);
        data.setOnDate(onDate);
        data.setOnTime(onTime);
        data.setServiceId(serviceId);
        data.setAppointmentId("0");

        return data;
    }

}
