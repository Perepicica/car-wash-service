package ru.perepichka.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class DiscountUpdateServiceDTO {

    @Range(min = 0, max = 100)
    private Integer discount;

}
