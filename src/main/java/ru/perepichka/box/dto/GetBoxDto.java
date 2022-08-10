package ru.perepichka.box.dto;

import lombok.Getter;
import lombok.Setter;
import ru.perepichka.user.dto.GetUserDto;

@Getter
@Setter
public class GetBoxDto {
    String id;
    String name;
    String opensAt;
    String closesAt;
    String workCoefficient;
    GetUserDto operator;
}
