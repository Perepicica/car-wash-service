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

import java.util.ArrayList;
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
        List<WashService> servicesForApp = getServices(data.getServicesId());
        Box box = getBox(data, servicesForApp);

        Appointment appointment = new Appointment();
        appointment.setDate(data.getOnDate());
        appointment.setStartsAt(data.getOnTime());
        appointment.setEndsAt(data.getOnTime().plusMinutes((long) (data.getDuration() * box.getWorkCoefficient())));
        appointment.setCost(getCostWithDiscount(servicesForApp));
        appointment.setBox(box);
        appointment.setService(servicesForApp.stream().findFirst().get());
        appointment.setCustomer(getCustomer(data.getCustomer().getId()));

        return appointmentRepository.save(appointment).getAsGetAppointmentDTO();
    }

    private List<WashService> getServices(List<String> servicesId) {
        List<WashService> services = new ArrayList<>();
        for (String id : servicesId) {
            Optional<WashService> service = washServiceRepository.findById(id);
            if (service.isEmpty()) {
                throw new IdNotFoundException("Service not found, id: " + id);
            }
            services.add(service.get());
        }
        return services;
    }

    private Box getBox(DataForBooking data, List<WashService> services) {
        data.setDuration(countDuration(services));
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

    private int countDuration(List<WashService> services) {
        int duration = 0;
        for (WashService service : services) {
            duration += service.getDuration();
        }
        return duration;
    }

    private User getCustomer(String id) {
        Optional<User> customer = userRepository.findById(id);
        if (customer.isEmpty()) {
            throw new IdNotFoundException("User not found, id: " + id);
        }
        return customer.get();
    }

    private float getCostWithDiscount(List<WashService> services) {
        float sum = 0;
        for (WashService service : services) {
            sum += (service.getCost() / 100.0) * (100 - service.getDiscount());
        }
        return sum;
    }
}
