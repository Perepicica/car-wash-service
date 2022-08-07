package ru.perepichka.box.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.box.Box;
import ru.perepichka.exception.InvalidCoefficientException;
import ru.perepichka.exception.InvalidTimeException;
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
        return box;
    }

    @JsonIgnore
    private LocalTime getLocalTime(String time){
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e){
            throw new InvalidTimeException();
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
