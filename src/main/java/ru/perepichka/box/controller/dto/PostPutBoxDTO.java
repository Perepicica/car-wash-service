package ru.perepichka.box.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.box.Box;
import ru.perepichka.user.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;

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
        box.setOpensAt(LocalTime.parse(opensAt));
        box.setClosesAt(LocalTime.parse(closesAt));
        box.setWorkCoefficient(Float.parseFloat(workCoefficient));
        User operator = new User();
        operator.setId(operatorId);
        box.setOperator(operator);
        return box;
    }
}
