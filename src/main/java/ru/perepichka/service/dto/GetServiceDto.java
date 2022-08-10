package ru.perepichka.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetServiceDto {
    private String id;
    private String name;
    private Integer duration;
    private Integer cost;
    private Integer discount;
}
