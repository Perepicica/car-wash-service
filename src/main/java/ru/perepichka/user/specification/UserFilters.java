package ru.perepichka.user.specification;

import lombok.*;
import ru.perepichka.user.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilters {
    private boolean isActive = true;
    private String email;
    private User.Role role;
}
