package ru.perepichka.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.exception.EmailAlreadyExistsException;
import ru.perepichka.exception.NoDataInDatabaseException;
import ru.perepichka.exception.IdNotFoundException;
import ru.perepichka.user.dto.GetUserDTO;
import ru.perepichka.user.specification.UserFilters;
import ru.perepichka.user.specification.UserSpecification;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final String INVALID_ID_EXC = "User not found, id: ";

    private final UserRepository userRepository;

    @Override
    public Page<GetUserDTO> getUsers(Pageable pageable) {
        Page<GetUserDTO> users = userRepository
                .findAll(UserSpecification.getFilteredUsers(new UserFilters()), pageable)
                .map(User::getAsGetUserDTO);

        if (users.isEmpty()) {
            throw new NoDataInDatabaseException();
        }
        return users;
    }

    @Override
    public GetUserDTO getUser(String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get().getAsGetUserDTO();
        } else {
            throw new IdNotFoundException(INVALID_ID_EXC + id);
        }
    }

    @Override
    public GetUserDTO createUser(User user) {
        UserFilters filters = UserFilters.builder().email(user.getEmail()).isActive(true).build();
        List<User> usersWithSameEmail = userRepository.findAll(UserSpecification.getFilteredUsers(filters));

        if (usersWithSameEmail.isEmpty()) {
            return userRepository.save(user).getAsGetUserDTO();
        }
        throw new EmailAlreadyExistsException(user.getEmail());
    }

    @Override
    public GetUserDTO updateUserRole(String id, User.Role newRole) {
        return userRepository.findById(id)
                .map(dbUser -> {
                    dbUser.setRole(newRole);
                    return userRepository.save(dbUser).getAsGetUserDTO();
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
}
