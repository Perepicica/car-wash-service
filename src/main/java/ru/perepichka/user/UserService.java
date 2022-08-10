package ru.perepichka.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.perepichka.appointment.dto.GetAppointmentForUserDto;
import ru.perepichka.user.dto.GetUserDto;

import java.util.List;

public interface UserService {
    Page<GetUserDto> getUsers(Pageable pageable);

    GetUserDto getUser(String id);

    List<GetAppointmentForUserDto> getUserAppointments(String id, boolean active);

    GetUserDto createUser(User user);

    GetUserDto updateUserRole(String id, User.Role newRole);

    void deleteUser(String id);
}
