package ru.perepichka.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.perepichka.exception.EmailAlreadyExistsException;
import ru.perepichka.exception.NoSuchIdException;
import ru.perepichka.user.User;
import ru.perepichka.user.UserServiceImpl;
import ru.perepichka.user.controller.dto.GetUserDTO;
import ru.perepichka.user.controller.dto.PostUserDTO;
import ru.perepichka.user.controller.dto.RoleUpdateUserDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<List<GetUserDTO>> getAllUsers() {
        List<GetUserDTO> users = userServiceImpl.getUsers()
                .stream()
                .map(User::getAsGetUserDTO)
                .toList();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable(name = "id") String id) {
        Optional<User> user = userServiceImpl.getUser(id);
        if (user.isPresent()) {
            GetUserDTO userResponse = user.get().getAsGetUserDTO();
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<GetUserDTO> createUser(@RequestBody @Valid PostUserDTO userDTO) {
        try {
            User userRequest = userDTO.getAsUser();
            User user = userServiceImpl.createUser(userRequest);
            GetUserDTO userResponse = user.getAsGetUserDTO();
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserDTO> updateUserRole(@PathVariable(name = "id") String id, @RequestBody @Valid RoleUpdateUserDTO statusDTO) {
        try {
            User user = userServiceImpl.updateUserRole(id, statusDTO.getAsUserRole());
            GetUserDTO userResponse = user.getAsGetUserDTO();
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") String id) {
        try {
            userServiceImpl.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchIdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
