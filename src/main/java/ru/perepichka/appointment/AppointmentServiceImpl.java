package ru.perepichka.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.perepichka.appointment.dto.DataForBooking;
import ru.perepichka.appointment.dto.GetAppointmentDTO;
import ru.perepichka.box.Box;
import ru.perepichka.box.BoxServiceImpl;
import ru.perepichka.exception.IdNotFoundException;
import ru.perepichka.exception.NoOptionsForBookingException;
import ru.perepichka.service.WashService;
import ru.perepichka.service.WashServiceRepository;
import ru.perepichka.user.User;
import ru.perepichka.user.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final WashServiceRepository washServiceRepository;
    private final UserRepository userRepository;
    private final BoxServiceImpl boxServiceImpl;

    @Override

    public GetAppointmentDTO createAppointment(DataForBooking data) {
        WashService serviceForApp = getServices(data.getServiceId());
        Box box = getBox(data, serviceForApp);

        Appointment appointment = new Appointment();
        appointment.setDate(data.getOnDate());
        appointment.setStartsAt(data.getOnTime());
        appointment.setEndsAt(data.getOnTime().plusMinutes((long) (data.getDuration() * box.getWorkCoefficient())));
        appointment.setCost(getCostWithDiscount(serviceForApp));
        appointment.setBox(box);
        appointment.setService(serviceForApp);
        appointment.setCustomer(getCustomer(data.getCustomerId()));

        return appointmentRepository.save(appointment).getAsGetAppointmentDTO();
    }

    private WashService getServices(String serviceId) {
        Optional<WashService> service = washServiceRepository.findById(serviceId);
        if (service.isEmpty()) {
            throw new IdNotFoundException("Service not found, id: " + serviceId);
        }
        return service.get();
    }

    private Box getBox(DataForBooking data, WashService service) {
        data.setDuration(service.getDuration());
        List<Box> boxes = boxServiceImpl.getAvailableBoxes(data);

        if (boxes.isEmpty()) {
            throw new NoOptionsForBookingException();
        }

        return getAvailableByWorkSchedule(boxes, data);
    }

    private Box getAvailableByWorkSchedule(List<Box> boxes, DataForBooking data) {
        Optional<Box> boxForAppointment = boxes.stream()
                .filter(box -> box.getOpensAt().isBefore(data.getOnTime()))
                .filter(box -> box.getClosesAt()
                        .isAfter(data.getOnTime()
                                .plusMinutes((long) (data.getDuration() * box.getWorkCoefficient()))))
                .findFirst();

        if (boxForAppointment.isEmpty()) {
            throw new NoOptionsForBookingException();
        }
        return boxForAppointment.get();
    }

    private User getCustomer(String id) {
        Optional<User> customer = userRepository.findById(id);
        if (customer.isEmpty()) {
            throw new IdNotFoundException("User not found, id: " + id);
        }
        return customer.get();
    }

    private float getCostWithDiscount(WashService service) {
        return (float) (service.getCost() / 100.0) * (100 - service.getDiscount());
    }
}
