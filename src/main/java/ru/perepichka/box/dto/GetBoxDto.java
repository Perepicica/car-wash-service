package ru.perepichka.box.dto;

import lombok.Getter;
import lombok.Setter;
import ru.perepichka.user.dto.GetUserDto;

import java.time.LocalTime;

@Getter
@Setter
public class GetBoxDto {
    private String id;
    private String name;
    private LocalTime opensAt;
    private LocalTime closesAt;
    private float workCoefficient;
    private GetUserDto operator;
}
