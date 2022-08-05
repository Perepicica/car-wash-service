package ru.perepichka.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.perepichka.user.controller.dto.GetUserDTO;

import javax.persistence.*;

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

    public GetUserDTO getAsGetUserDTO() {
        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setId(id);
        getUserDTO.setName(name);
        getUserDTO.setEmail(email);
        getUserDTO.setRole(role.name());
        return getUserDTO;
    }

    public enum Role {
        CUSTOMER, OPERATOR, ADMIN
    }

}
