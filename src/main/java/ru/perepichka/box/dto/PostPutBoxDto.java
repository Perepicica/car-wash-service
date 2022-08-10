package ru.perepichka.box.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.box.Box;
import ru.perepichka.user.User;
import ru.perepichka.util.DateTimeParserUtil;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class PostPutBoxDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String opensAt;
    @NotEmpty
    private String closesAt;
    @Positive
    private float workCoefficient;
    @NotEmpty
    private String operatorId;

    @JsonIgnore
    public Box getAsBox() {
        Box box = new Box();
        box.setName(name);
        box.setOpensAt(DateTimeParserUtil.getLocalTime(opensAt));
        box.setClosesAt(DateTimeParserUtil.getLocalTime(closesAt));
        box.setWorkCoefficient(workCoefficient);
        User operator = new User();
        operator.setId(operatorId);
        box.setOperator(operator);
        box.setActive(true);
        return box;
    }

}
