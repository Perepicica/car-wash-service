package ru.perepichka.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    Optional<User> getUser(String id);

    User createUser(User user);

    User updateUserRole(String id, User.Role newRole);

    void deleteUser(String id);
}
