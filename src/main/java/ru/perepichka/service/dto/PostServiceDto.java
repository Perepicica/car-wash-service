package ru.perepichka.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import ru.perepichka.service.WashService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class PostServiceDto {
    @NotEmpty
    private String name;
    @Positive
    private Integer duration;
    @Positive
    private Integer cost;
    @Range(min = 0, max = 100)
    private Integer discount;

    @JsonIgnore
    public WashService getAsService(){
        WashService service = new WashService();
        service.setName(name);
        service.setDuration(duration);
        service.setCost(cost);
        service.setDiscount(discount);
        return service;
    }
}
