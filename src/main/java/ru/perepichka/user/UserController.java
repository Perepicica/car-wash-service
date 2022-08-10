package ru.perepichka.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.appointment.dto.GetAppointmentForUserDto;
import ru.perepichka.user.dto.GetUserDto;
import ru.perepichka.user.dto.PostUserDto;
import ru.perepichka.user.dto.RoleUpdateUserDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GetUserDto> getAllUsers(Pageable pageable) {
        return userServiceImpl.getUsers(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GetUserDto getUserById(@PathVariable(name = "id") String id) {
        return userServiceImpl.getUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/appointments")
    public List<GetAppointmentForUserDto> getUserAppointments(@PathVariable(name = "id") String id,
                                                              @RequestParam(value = "active", required = false, defaultValue = "true") boolean active) {
        return userServiceImpl.getUserAppointments(id, active);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GetUserDto createUser(@RequestBody @Valid PostUserDto userDTO) {
        return userServiceImpl.createUser(userDTO.getAsUser());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public GetUserDto updateUserRole(@PathVariable(name = "id") String id,
                                     @RequestBody @Valid RoleUpdateUserDto statusDTO) {
        return userServiceImpl.updateUserRole(id, statusDTO.getAsUserRole());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") String id) {
        userServiceImpl.deleteUser(id);
    }

}
