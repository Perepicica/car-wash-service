package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.util.DateTimeParserUtil;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PostAppointmentDto {
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
        data.setOnDate(DateTimeParserUtil.getLocalDate(onDate));
        data.setOnTime(DateTimeParserUtil.getLocalTime(onTime));
        data.setServiceId(serviceId);
        data.setAppointmentId("0");

        return data;
    }

}
