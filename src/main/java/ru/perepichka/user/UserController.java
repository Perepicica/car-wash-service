package ru.perepichka.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.appointment.dto.GetAppointmentForUserDTO;
import ru.perepichka.user.dto.GetUserDTO;
import ru.perepichka.user.dto.PostUserDTO;
import ru.perepichka.user.dto.RoleUpdateUserDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GetUserDTO> getAllUsers(Pageable pageable) {
        return userServiceImpl.getUsers(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GetUserDTO getUserById(@PathVariable(name = "id") String id) {
        return userServiceImpl.getUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}/appointments")
    public List<GetAppointmentForUserDTO> getUserAppointments(@PathVariable(name = "id") String id,
                                                              @RequestParam(value = "active", required = false, defaultValue = "true") boolean active) {
        return userServiceImpl.getUserAppointments(id, active);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public GetUserDTO createUser(@RequestBody @Valid PostUserDTO userDTO) {
        return userServiceImpl.createUser(userDTO.getAsUser());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public GetUserDTO updateUserRole(@PathVariable(name = "id") String id,
                                     @RequestBody @Valid RoleUpdateUserDTO statusDTO) {
        return userServiceImpl.updateUserRole(id, statusDTO.getAsUserRole());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") String id) {
        userServiceImpl.deleteUser(id);
    }

}
