package ru.perepichka.appointment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.exception.InvalidDateTimeException;
import ru.perepichka.user.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Getter
@Setter
public class PostAppointmentDTO {
    @NotEmpty
    String customerId;
    @NotEmpty
    String onDate;
    @NotEmpty
    String onTime;
    @NotEmpty
    List<String> services;

    @JsonIgnore
    public DataForBooking getAsDataForBooking(){
        DataForBooking data = new DataForBooking();

        User customer = new User();
        customer.setId(customerId);
        data.setCustomer(customer);

        data.setOnDate(getLocalDate(onDate));
        data.setOnTime(getLocalTime(onTime));

        data.setServicesId(services);

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
