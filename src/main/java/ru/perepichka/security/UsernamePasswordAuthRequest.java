package ru.perepichka.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernamePasswordAuthRequest {
    private String email;
    private String password;
}
