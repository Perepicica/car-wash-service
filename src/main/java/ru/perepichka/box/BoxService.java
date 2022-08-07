package ru.perepichka.box;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.perepichka.box.controller.dto.GetBoxDTO;

public interface BoxService {

    Page<GetBoxDTO> getAllBoxes(Pageable pageable);

    GetBoxDTO getBox(String id);

    GetBoxDTO createBox(Box box);

    GetBoxDTO updateBox(String id, Box newBox);

    void deleteBox(String id);
}
