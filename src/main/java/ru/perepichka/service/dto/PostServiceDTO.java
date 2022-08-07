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
public class PostServiceDTO {
    @NotEmpty
    String name;
    @Positive
    Integer duration;
    @Positive
    Integer cost;
    @Range(min = 0, max = 100)
    Integer discount;

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
