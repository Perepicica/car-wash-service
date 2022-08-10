package ru.perepichka.user;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import ru.perepichka.appointment.Appointment;
import ru.perepichka.box.Box;
import ru.perepichka.user.dto.GetUserDto;
import ru.perepichka.user.dto.UserFullDto;

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
    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(mappedBy = "customer",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonManagedReference
    @Fetch(FetchMode.JOIN)
    private Set<Appointment> appointments;

    @OneToOne(mappedBy = "operator",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonBackReference
    private Box box;

    public GetUserDto getAsGetUserDto() {
        GetUserDto dto = new GetUserDto();
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setRole(role);
        return dto;
    }

    public UserFullDto getAsUserFullDto(){
        UserFullDto dto = new UserFullDto();
        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setRole(role);
        return dto;
    }

}
