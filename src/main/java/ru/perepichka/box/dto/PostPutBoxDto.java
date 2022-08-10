package ru.perepichka.box.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.box.Box;
import ru.perepichka.user.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;

@Getter
@Setter
public class PostPutBoxDto {
    @NotEmpty
    private String name;
    @NotNull
    private LocalTime opensAt;
    @NotNull
    private LocalTime closesAt;
    @Positive
    private float workCoefficient;
    @NotEmpty
    private String operatorId;

    @JsonIgnore
    public Box getAsBox() {
        Box box = new Box();
        box.setName(name);
        box.setOpensAt(opensAt);
        box.setClosesAt(closesAt);
        box.setWorkCoefficient(workCoefficient);
        User operator = new User();
        operator.setId(operatorId);
        box.setOperator(operator);
        box.setActive(true);
        return box;
    }

}
