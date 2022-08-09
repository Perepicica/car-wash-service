package ru.perepichka.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.perepichka.appointment.dto.GetAppointmentForUserDTO;
import ru.perepichka.user.dto.GetUserDTO;

import java.util.List;

public interface UserService {
    Page<GetUserDTO> getUsers(Pageable pageable);

    GetUserDTO getUser(String id);

    List<GetAppointmentForUserDTO> getUserAppointments(String id);

    GetUserDTO createUser(User user);

    GetUserDTO updateUserRole(String id, User.Role newRole);

    void deleteUser(String id);
}
