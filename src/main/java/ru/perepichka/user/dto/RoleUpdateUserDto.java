package ru.perepichka.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.exception.InvalidRoleTypeException;
import ru.perepichka.user.User;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RoleUpdateUserDto {
    @NotEmpty
    String role;

    @JsonIgnore
    public User.Role getAsUserRole() {
        for (User.Role rol : User.Role.values()) {
            if (rol.name().equals(role.toUpperCase())) return rol;
        }
        throw new InvalidRoleTypeException(role);
    }
}
