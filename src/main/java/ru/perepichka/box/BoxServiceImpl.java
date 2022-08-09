package ru.perepichka.box;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.appointment.dto.DataForBooking;
import ru.perepichka.box.dto.GetBoxDTO;
import ru.perepichka.exception.IdNotFoundException;
import ru.perepichka.exception.NoDataInDatabaseException;
import ru.perepichka.exception.OperatorAssigningException;
import ru.perepichka.user.User;
import ru.perepichka.user.UserRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class BoxServiceImpl implements BoxService {

    private static final String INVALID_ID_EXC = "Box not found, id: ";
    private static final String INVALID_OPERATOR_ID_EXC = "Operator not found, id: ";

    private final BoxRepository boxRepository;
    private final UserRepository userRepository;

    @Override
    public Page<GetBoxDTO> getAllBoxes(Pageable pageable) {
        Page<GetBoxDTO> boxes = boxRepository
                .findAll(pageable)
                .map(Box::getAsGetBoxDTO);

        if (boxes.isEmpty()) {
            throw new NoDataInDatabaseException();
        }
        return boxes;
    }

    @Override
    public GetBoxDTO getBox(String id) {
        Optional<Box> box = boxRepository.findById(id);

        if (box.isPresent()) {
            return box.get().getAsGetBoxDTO();
        }
        throw new IdNotFoundException(INVALID_ID_EXC + id);
    }

    @Override
    public GetBoxDTO createBox(Box box) {
        box.setOperator(getAndCheckOperator(box.getOperator().getId()));
        return boxRepository.save(box).getAsGetBoxDTO();
    }

    @Override
    public GetBoxDTO updateBox(String id, Box newBox) {
        return boxRepository.findById(id).map(box -> {
            box.setName(newBox.getName());
            box.setOpensAt(newBox.getOpensAt());
            box.setClosesAt(newBox.getClosesAt());
            box.setWorkCoefficient(newBox.getWorkCoefficient());
            box.setOperator(getAndCheckOperator(newBox.getOperator().getId()));
            return boxRepository.save(box).getAsGetBoxDTO();
        }).orElseThrow(() -> new IdNotFoundException(INVALID_ID_EXC + id));
    }

    @Override
    public void deleteBox(String id) {
        try {
            boxRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new IdNotFoundException(INVALID_ID_EXC + id);
        }
    }

    @Override
    public List<Box> getAvailableBoxes(DataForBooking data) {
        return boxRepository.getAvailableBoxes(
                data.getOnDate(),
                data.getOnTime(),
                data.getDuration(),
                data.getAppointmentId());
    }

    private User getAndCheckOperator(String id) {
        Optional<User> operator = userRepository.findById(id);

        if (operator.isEmpty()) {
            throw new IdNotFoundException(INVALID_OPERATOR_ID_EXC + id);
        }

        if (operator.get().getRole() != User.Role.OPERATOR) {
            throw new OperatorAssigningException("This user isn't an operator");
        }

        if (operator.get().getBox() != null) {
            throw new OperatorAssigningException(
                    "This operator is already assigned to box " + operator.get().getBox().getName()
            );
        }

        return operator.get();
    }

}
