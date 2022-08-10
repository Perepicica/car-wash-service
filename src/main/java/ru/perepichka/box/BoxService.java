package ru.perepichka.box;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.perepichka.appointment.dto.DataForBooking;
import ru.perepichka.box.dto.GetBoxDto;

import java.util.List;

public interface BoxService {

    Page<GetBoxDto> getAllBoxes(Pageable pageable);

    GetBoxDto getBox(String id);

    GetBoxDto createBox(Box box);

    GetBoxDto updateBox(String id, Box newBox);

    void deleteBox(String id);

    List<Box> getAvailableBoxes(DataForBooking data);
}
