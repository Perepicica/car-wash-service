package ru.perepichka.appointment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.perepichka.box.Box;
import ru.perepichka.service.WashService;
import ru.perepichka.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "varchar")
    private String id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "status")
    private Status status = Status.BOOKED;

    @Column(name = "cost")
    private Float cost;

    @Column(name = "duration")
    private Float duration;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private User customer;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "box_id")
    @JsonBackReference
    private Box box;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "service_id")
    @JsonBackReference
    private WashService service;

    public enum Status {
        BOOKED, CONFIRMED, CANCELED, DONE
    }
}
