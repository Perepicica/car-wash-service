package ru.perepichka.box.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.box.Box;
import ru.perepichka.exception.InvalidCoefficientException;
import ru.perepichka.exception.InvalidDateTimeException;
import ru.perepichka.user.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class PostPutBoxDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String opensAt;
    @NotEmpty
    private String closesAt;
    @NotEmpty
    private String workCoefficient;
    @NotEmpty
    private String operatorId;

    @JsonIgnore
    public Box getAsBox() {
        Box box = new Box();
        box.setName(name);
        box.setOpensAt(getLocalTime(opensAt));
        box.setClosesAt(getLocalTime(closesAt));
        box.setWorkCoefficient(getCoefficient());
        User operator = new User();
        operator.setId(operatorId);
        box.setOperator(operator);
        box.setActive(true);
        return box;
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
    private Float getCoefficient(){
        try {
            return Float.parseFloat(workCoefficient);
        } catch (Exception e){
            e.printStackTrace();
            throw new InvalidCoefficientException();
        }
    }
}
