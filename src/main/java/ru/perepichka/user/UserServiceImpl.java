package ru.perepichka.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.appointment.Appointment;
import ru.perepichka.appointment.dto.GetAppointmentForUserDto;
import ru.perepichka.exception.EmailAlreadyExistsException;
import ru.perepichka.exception.NoDataInDatabaseException;
import ru.perepichka.exception.IdNotFoundException;
import ru.perepichka.user.dto.GetUserDto;
import ru.perepichka.user.specification.UserFilters;
import ru.perepichka.user.specification.UserSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final String INVALID_ID_EXC = "User not found, id: ";

    private final UserRepository userRepository;

    @Override
    public Page<GetUserDto> getUsers(Pageable pageable) {
        Page<GetUserDto> users = userRepository
                .findAll(UserSpecification.getFilteredUsers(new UserFilters()), pageable)
                .map(User::getAsGetUserDto);

        if (users.isEmpty()) {
            throw new NoDataInDatabaseException();
        }
        return users;
    }

    @Override
    public GetUserDto getUser(String id) {
        Optional<User> user = userRepository.findById(id);
        return checkIfUserExists(user, id).getAsGetUserDto();
    }

    @Override
    public List<GetAppointmentForUserDto> getUserAppointments(String id, boolean active) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = checkIfUserExists(optionalUser, id);

        Stream<Appointment> appStream = user.getAppointments().stream();

        if (active) {
            appStream = appStream
                    .filter(app -> app.getStatus().equals(Appointment.Status.BOOKED)
                            || app.getStatus().equals(Appointment.Status.CONFIRMED));
        } else {
            appStream = appStream
                    .filter(app -> app.getStatus().equals(Appointment.Status.DONE));
        }

        return appStream.map(Appointment::getAsAppointmentForUserDto).toList();
    }

    @Override
    public GetUserDto createUser(User user) {
        UserFilters filters = UserFilters.builder().email(user.getEmail()).isActive(true).build();
        List<User> usersWithSameEmail = userRepository.findAll(UserSpecification.getFilteredUsers(filters));

        if (usersWithSameEmail.isEmpty()) {
            return userRepository.save(user).getAsGetUserDto();
        }
        throw new EmailAlreadyExistsException(user.getEmail());
    }

    @Override
    public GetUserDto updateUserRole(String id, User.Role newRole) {
        return userRepository.findById(id)
                .map(dbUser -> {
                    dbUser.setRole(newRole);
                    return userRepository.save(dbUser).getAsGetUserDto();
                })
                .orElseThrow(() -> new IdNotFoundException(INVALID_ID_EXC + id));
    }

    @Override
    public void deleteUser(String id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setActive(false);
            userRepository.save(user);
        } else {
            throw new IdNotFoundException(INVALID_ID_EXC + id);
        }
    }

    public User checkIfUserExists(Optional<User> user, String id) {
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IdNotFoundException(INVALID_ID_EXC + id);
        }
    }
}
