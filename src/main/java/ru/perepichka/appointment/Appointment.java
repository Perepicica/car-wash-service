package ru.perepichka.appointment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.perepichka.appointment.dto.GetAppointmentDTO;
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

    @Column(name = "app_date")
    private LocalDate date;

    @Column(name = "starts_at")
    private LocalTime startsAt;

    @Column(name = "ends_at")
    private LocalTime endsAt;

    @Column(name = "status")
    private Status status = Status.BOOKED;

    @Column(name = "cost")
    private Float cost;


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

    public GetAppointmentDTO getAsGetAppointmentDTO() {
        GetAppointmentDTO dto = new GetAppointmentDTO();
        dto.setId(id);
        dto.setDate(date);
        dto.setStartsAt(startsAt);
        dto.setEndsAt(endsAt);
        dto.setStatus(status.name());
        dto.setCost(cost);
        dto.setCustomer(customer.getAsGetUserDTO());
        dto.setBox(box.getAsGetBoxDTO());
        dto.setService(service.getAsGetServiceDTO());
        return dto;

    }

    public enum Status {
        BOOKED, CONFIRMED, CANCELED, DONE
    }
}
