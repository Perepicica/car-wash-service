package ru.perepichka.box;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.exception.NoSuchIdException;
import ru.perepichka.user.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public List<Box> getAllBoxes() {
        return boxRepository.findAll();
    }

    @Override
    public Optional<Box> getBox(String id) {
        return boxRepository.findById(id);
    }

    @Override
    public Box createBox(Box box) {
        box.setOperator(userServiceImpl.getUser(box.getOperator().getId()).get());
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
        }).orElseThrow(() -> new NoSuchIdException("Box with such id: " + id + " not found"));
    }

    @Override
    public void deleteBox(String id) {
        boxRepository.deleteById(id);
    }

}
