package ru.perepichka.user.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.perepichka.user.User;
import ru.perepichka.user.User_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification {

    private UserSpecification(){}

    public static Specification<User> getFilteredUsers(UserFilters filters) {
        return (root, query, cb) -> cb.and(
                filterByActive(cb, root, filters.isActive()),
                filterByEmail(cb, root, filters.getEmail()),
                filterByRole(cb, root, filters.getRole()));
    }

    private static Predicate filterByActive(CriteriaBuilder cb, Root<User> root, boolean active) {
        if (active) return cb.isTrue(root.get(User_.IS_ACTIVE));
        return cb.and();
    }

    private static Predicate filterByEmail(CriteriaBuilder cb, Root<User> root, String email) {
        if (email == null) return cb.and();
        return cb.equal(root.get(User_.EMAIL), email);
    }

    private static Predicate filterByRole(CriteriaBuilder cb, Root<User> root, User.Role role) {
        if (role == null) return cb.and();
        return cb.equal(root.get(User_.ROLE), role);
    }
}
