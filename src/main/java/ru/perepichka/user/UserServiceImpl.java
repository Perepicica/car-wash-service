package ru.perepichka.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.exception.EmailAlreadyExistsException;
import ru.perepichka.exception.NoSuchIdException;
import ru.perepichka.user.specification.UserFilters;
import ru.perepichka.user.specification.UserSpecification;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll(UserSpecification.getFilteredUsers(new UserFilters()));
    }

    @Override
    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        UserFilters filters = UserFilters.builder().email(user.getEmail()).build();
        List<User> usersWithSameEmail = userRepository.findAll(UserSpecification.getFilteredUsers(filters));
        if (!usersWithSameEmail.isEmpty()) {
            throw new EmailAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUserRole(String id, User.Role newRole) {
        return userRepository.findById(id)
                .map(dbUser -> {
                    dbUser.setRole(newRole);
                    return userRepository.save(dbUser);
                })
                .orElseThrow(() -> new NoSuchIdException("Employee with such id: " + id + "not found"));
    }

    @Override
    public void deleteUser(String id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            User user = optUser.get();
            user.setActive(false);
            userRepository.save(user);
        } else {
            throw new NoSuchIdException("No user with such id: " + id);
        }
    }
}
