package ru.perepichka.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.exception.InvalidUserRoleException;
import ru.perepichka.user.Role;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RoleUpdateUserDto {
    @NotEmpty
    private String role;

    @JsonIgnore
    public Role getAsUserRole() {
        for (Role rol : Role.values()) {
            if (rol.name().equals(role.toUpperCase())) return rol;
        }
        throw new InvalidUserRoleException(role);
    }
}
