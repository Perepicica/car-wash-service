package ru.perepichka.user.specification;

import lombok.*;
import ru.perepichka.user.Role;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilters {
    private boolean isActive = true;
    private String email;
    private Role role;
}
