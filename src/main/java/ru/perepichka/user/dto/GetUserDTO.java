package ru.perepichka.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserDTO {
    private String id;
    private String name;
    private String email;
    private String role;
}
