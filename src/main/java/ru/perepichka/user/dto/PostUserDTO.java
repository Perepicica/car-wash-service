package ru.perepichka.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.perepichka.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PostUserDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    @JsonIgnore
    public User getAsUser() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

}
