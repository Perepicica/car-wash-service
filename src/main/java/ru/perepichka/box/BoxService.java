package ru.perepichka.box;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.perepichka.box.controller.dto.GetBoxDTO;

import java.util.Optional;

public interface BoxService {

    Page<GetBoxDTO> getAllBoxes(Pageable pageable);

    Optional<Box> getBox(String id);

    Box createBox(Box box);

    Box updateBox(String id, Box newBox);

    void deleteBox(String id);
}
