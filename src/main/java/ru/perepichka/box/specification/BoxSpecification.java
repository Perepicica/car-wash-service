package ru.perepichka.box.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.perepichka.box.Box;
import ru.perepichka.box.Box_;

public class BoxSpecification {

    private BoxSpecification(){}

    public static Specification<Box> getActiveBoxes() {
        return (root, query, cb) ->
                cb.isTrue(root.get(Box_.IS_ACTIVE));
    }
}
