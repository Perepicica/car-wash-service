package ru.perepichka.box.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.box.Box;
import ru.perepichka.exception.InvalidCoefficientException;
import ru.perepichka.user.User;
import ru.perepichka.util.DateTimeParserUtil;

import javax.validation.constraints.NotEmpty;


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
        box.setOpensAt(DateTimeParserUtil.getLocalTime(opensAt));
        box.setClosesAt(DateTimeParserUtil.getLocalTime(closesAt));
        box.setWorkCoefficient(getCoefficient());
        User operator = new User();
        operator.setId(operatorId);
        box.setOperator(operator);
        box.setActive(true);
        return box;
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
