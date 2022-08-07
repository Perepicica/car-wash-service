package ru.perepichka.box.dto;

import lombok.Getter;
import lombok.Setter;
import ru.perepichka.user.dto.GetUserDTO;

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
