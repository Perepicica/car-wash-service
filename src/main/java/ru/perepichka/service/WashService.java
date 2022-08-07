package ru.perepichka.service;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.perepichka.service.dto.GetServiceDTO;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "service")
public class WashService {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "varchar")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "discount")
    private Integer discount;

    public GetServiceDTO getAsGetServiceDTO(){
        GetServiceDTO dto = new GetServiceDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setDuration(duration);
        dto.setCost(cost);
        dto.setDiscount(discount);
        return dto;
    }
}
