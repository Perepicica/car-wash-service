package ru.perepichka.user.dto;

import lombok.Getter;
import lombok.Setter;
import ru.perepichka.user.Role;

@Getter
@Setter
public class GetUserDto {
    private String id;
    private String name;
    private String email;
    private Role role;
}
