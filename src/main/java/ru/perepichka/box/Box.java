package ru.perepichka.box;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.perepichka.box.dto.GetBoxDto;
import ru.perepichka.user.User;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
@Table(name = "box")
public class Box {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "varchar")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "opens_at")
    private LocalTime opensAt;

    @Column(name = "closes_at")
    private LocalTime closesAt;

    @Column(name = "work_coefficient")
    private Float workCoefficient;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "operator_id")
    private User operator;

    public GetBoxDto getAsGetBoxDto() {
        GetBoxDto dto = new GetBoxDto();
        dto.setId(id);
        dto.setName(name);
        dto.setOpensAt(opensAt);
        dto.setClosesAt(closesAt);
        dto.setWorkCoefficient(workCoefficient);
        dto.setOperator(operator.getAsGetUserDto());
        return dto;
    }
}
