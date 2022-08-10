package ru.perepichka.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.appointment.Appointment;
import ru.perepichka.appointment.dto.GetAppointmentForUserDto;
import ru.perepichka.exception.EmailAlreadyExistsException;
import ru.perepichka.exception.IdNotFoundException;
import ru.perepichka.exception.UserNotFoundException;
import ru.perepichka.user.dto.GetUserDto;
import ru.perepichka.user.dto.UserFullDto;
import ru.perepichka.user.specification.UserFilters;
import ru.perepichka.user.specification.UserSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_EXC = "User not found";

    private final UserRepository userRepository;

    @Override
    public UserFullDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        if (!user.isActive()) {
            throw new UserNotFoundException();
        }
        return user.getAsUserFullDto();
    }

    @Override
    public Page<GetUserDto> getUsers(Pageable pageable) {
        return userRepository
                .findAll(UserSpecification.getFilteredUsers(new UserFilters()), pageable)
                .map(User::getAsGetUserDto);
    }

    @Override
    public GetUserDto getUser(String id) {
        Optional<User> user = userRepository.findById(id);
        return checkIfUserExists(user).getAsGetUserDto();
    }

    @Override
    public List<GetAppointmentForUserDto> getUserAppointments(String id, boolean active) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = checkIfUserExists(optionalUser);

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
    public GetUserDto updateUserRole(String id, Role newRole) {
        return userRepository.findById(id)
                .map(dbUser -> {
                    dbUser.setRole(newRole);
                    return userRepository.save(dbUser).getAsGetUserDto();
                })
                .orElseThrow(() -> new IdNotFoundException(USER_NOT_FOUND_EXC));
    }

    @Override
    public void deleteUser(String id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setActive(false);
            userRepository.save(user);
        } else {
            throw new IdNotFoundException(USER_NOT_FOUND_EXC);
        }
    }

    public User checkIfUserExists(Optional<User> user) {
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IdNotFoundException(USER_NOT_FOUND_EXC);
        }
    }
}
