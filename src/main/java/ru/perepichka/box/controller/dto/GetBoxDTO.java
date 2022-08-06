package ru.perepichka.box.controller.dto;

import lombok.Getter;
import lombok.Setter;
import ru.perepichka.user.controller.dto.GetUserDTO;

@Getter
@Setter
public class GetBoxDTO {
    String id;
    String name;
    String opensAt;
    String closesAt;
    String workCoefficient;
    GetUserDTO operator;
}
