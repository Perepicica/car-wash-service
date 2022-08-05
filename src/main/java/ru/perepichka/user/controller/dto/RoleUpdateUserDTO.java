package ru.perepichka.user.controller.dto;

import lombok.Getter;
import lombok.Setter;
import ru.perepichka.user.User;

@Getter
@Setter
public class RoleUpdateUserDTO {
    String role;

    public User.Role getAsUserRole() {
        for (User.Role rol : User.Role.values()) {
            if (rol.name().equals(role.toUpperCase())) return rol;
        }
        throw new IllegalArgumentException();
    }
}
