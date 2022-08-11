package ru.perepichka.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.appointment.dto.GetAppointmentForUserDto;
import ru.perepichka.user.dto.GetUserDto;
import ru.perepichka.user.dto.RoleUpdateUserDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<GetUserDto> getAllUsers(Pageable pageable) {
        return userServiceImpl.getUsers(pageable);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CUSTOMER') and #id==authentication.principal.getId)")
    @GetMapping("/{id}")
    public GetUserDto getUserById(@PathVariable(name = "id") String id) {
        return userServiceImpl.getUser(id);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CUSTOMER') and #id==authentication.principal.getId)")
    @GetMapping("/{id}/appointments")
    public List<GetAppointmentForUserDto> getUserAppointments(@PathVariable(name = "id") String id,
                                                              @RequestParam(value = "active", required = false, defaultValue = "true") boolean active) {
        return userServiceImpl.getUserAppointments(id, active);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public GetUserDto updateUserRole(@PathVariable(name = "id") String id,
                                     @RequestBody @Valid RoleUpdateUserDto statusDTO) {
        return userServiceImpl.updateUserRole(id, statusDTO.getAsUserRole());
    }

    @PreAuthorize("hasRole('CUSTOMER') and #id==authentication.principal.getId")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") String id) {
        userServiceImpl.deleteUser(id);
    }

}
