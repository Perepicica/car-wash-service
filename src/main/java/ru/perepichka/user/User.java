package ru.perepichka.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.perepichka.user.dto.GetUserDTO;

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
