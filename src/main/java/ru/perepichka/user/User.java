package ru.perepichka.user;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import ru.perepichka.appointment.Appointment;
import ru.perepichka.user.dto.GetUserDTO;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "db_user")
public class User {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "varchar")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private Role role = Role.CUSTOMER;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(
            mappedBy = "customer",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
            })
    @JsonManagedReference
    @Fetch(FetchMode.JOIN)
    private Set<Appointment> appointments;

    public GetUserDTO getAsGetUserDTO() {
        GetUserDTO dto = new GetUserDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setRole(role.name());
        return dto;
    }

    public enum Role {
        CUSTOMER, OPERATOR, ADMIN
    }

}
