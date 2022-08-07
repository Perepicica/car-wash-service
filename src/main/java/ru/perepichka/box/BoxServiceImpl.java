package ru.perepichka.box;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.box.controller.dto.GetBoxDTO;
import ru.perepichka.exception.IdNotFoundException;
import ru.perepichka.exception.NoDataInDatabaseException;
import ru.perepichka.user.UserServiceImpl;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public Page<GetBoxDTO> getAllBoxes(Pageable pageable) {
        Page<GetBoxDTO> boxes = boxRepository
                .findAll(pageable)
                .map(Box::getAsGetBoxDTO);
        if(boxes.isEmpty()){
            throw new NoDataInDatabaseException();
        }
        return boxes;
    }

    @Override
    public Optional<Box> getBox(String id) {
        return boxRepository.findById(id);
    }

    @Override
    public Box createBox(Box box) {
        return boxRepository.save(box);
    }

    @Override
    public Box updateBox(String id, Box newBox) {
        return boxRepository.findById(id).map(box -> {
            box.setName(newBox.getName());
            box.setOpensAt(newBox.getOpensAt());
            box.setClosesAt(newBox.getClosesAt());
            box.setWorkCoefficient(newBox.getWorkCoefficient());
            box.setOperator(newBox.getOperator());
            return boxRepository.save(box);
        }).orElseThrow(() -> new IdNotFoundException("Box with such id: " + id + " not found"));
    }

    @Override
    public void deleteBox(String id) {
        boxRepository.deleteById(id);
    }

}
