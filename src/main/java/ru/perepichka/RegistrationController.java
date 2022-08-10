package ru.perepichka;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.user.UserServiceImpl;
import ru.perepichka.user.dto.GetUserDto;
import ru.perepichka.user.dto.PostUserDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/registration")
public class RegistrationController {

    private final UserServiceImpl userServiceImpl;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GetUserDto register(@RequestBody @Valid PostUserDto userDTO) {
        return userServiceImpl.createUser(userDTO.getAsUser());
    }

}
