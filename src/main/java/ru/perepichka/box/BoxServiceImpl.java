package ru.perepichka.box;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.appointment.Appointment;
import ru.perepichka.appointment.AppointmentRepository;
import ru.perepichka.appointment.dto.DataForBooking;
import ru.perepichka.appointment.specification.AppointmentsSpecification;
import ru.perepichka.box.dto.GetBoxDto;
import ru.perepichka.box.specification.BoxSpecification;
import ru.perepichka.exception.DeleteBoxException;
import ru.perepichka.exception.IdNotFoundException;
import ru.perepichka.exception.OperatorToBoxAssigningException;
import ru.perepichka.user.Role;
import ru.perepichka.user.User;
import ru.perepichka.user.UserRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class BoxServiceImpl implements BoxService {

    private static final String INVALID_ID_EXC = "Box not found";
    private static final String INVALID_OPERATOR_ID_EXC = "Operator not found";
    private static final String OPERATOR_HAS_ALREADY_BOX_EXC = "This operator is already assigned to box ";
    private static final String USER_IS_NOT_OPERATOR_EXC = "This user isn't an operator";
    private static final String BOX_HAS_APPOINTMENTS_EXC = "This box has booked ar confirmed appointments";

    private final BoxRepository boxRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public Page<GetBoxDto> getAllBoxes(Pageable pageable) {
        return boxRepository
                .findAll(BoxSpecification.getActiveBoxes(), pageable)
                .map(Box::getAsGetBoxDto);
    }

    @Override
    public GetBoxDto getBox(String id) {
        return getAndCheckBox(id).getAsGetBoxDto();
    }

    @Override
    public GetBoxDto createBox(Box box) {
        box.setOperator(getAndCheckOperator(box.getOperator().getId()));
        return boxRepository.save(box).getAsGetBoxDto();
    }

    @Override
    public GetBoxDto updateBox(String id, Box newBox) {
        return boxRepository.findById(id).map(box -> {
            box.setName(newBox.getName());
            box.setOpensAt(newBox.getOpensAt());
            box.setClosesAt(newBox.getClosesAt());
            box.setWorkCoefficient(newBox.getWorkCoefficient());
            box.setOperator(getAndCheckOperator(newBox.getOperator().getId()));
            return boxRepository.save(box).getAsGetBoxDto();
        }).orElseThrow(() -> new IdNotFoundException(INVALID_ID_EXC));
    }

    @Override
    public void deleteBox(String id) {
        checkAppointments(id);
        Box box = getAndCheckBox(id);

        box.getOperator().setBox(null);
        box.setOperator(null);
        box.setActive(false);

        boxRepository.save(box);
    }

    private Box getAndCheckBox(String id) {
        Optional<Box> box = boxRepository.findById(id);

        if (box.isPresent()) {
            return box.get();
        }
        throw new IdNotFoundException(INVALID_ID_EXC);
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
            throw new IdNotFoundException(INVALID_OPERATOR_ID_EXC);
        }

        if (operator.get().getRole() != Role.OPERATOR) {
            throw new OperatorToBoxAssigningException(USER_IS_NOT_OPERATOR_EXC);
        }

        if (operator.get().getBox() != null) {
            throw new OperatorToBoxAssigningException(
                    OPERATOR_HAS_ALREADY_BOX_EXC + operator.get().getBox().getName()
            );
        }

        return operator.get();
    }

    private void checkAppointments(String id) {
        List<Appointment> appointments =
                appointmentRepository.findAll(AppointmentsSpecification.getUndoneAppointmentsForBox(id));
        if (!appointments.isEmpty()) {
            throw new DeleteBoxException(BOX_HAS_APPOINTMENTS_EXC);
        }
    }
}
