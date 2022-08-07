package ru.perepichka.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetServiceDTO {
    private String id;
    String name;
    Integer duration;
    Integer cost;
    Integer discount;
}
