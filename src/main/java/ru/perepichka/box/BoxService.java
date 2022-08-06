package ru.perepichka.box;

import java.util.List;
import java.util.Optional;

public interface BoxService {

    List<Box> getAllBoxes();

    Optional<Box> getBox(String id);

    Box createBox(Box box);

    Box updateBox(String id, Box newBox);

    void deleteBox(String id);
}
