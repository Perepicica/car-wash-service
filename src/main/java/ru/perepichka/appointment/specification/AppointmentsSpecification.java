package ru.perepichka.appointment.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.perepichka.appointment.Appointment;
import ru.perepichka.appointment.Appointment_;
import ru.perepichka.box.Box;
import ru.perepichka.box.Box_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentsSpecification {

    private AppointmentsSpecification(){}
    
    public static Specification<Appointment> getFilteredAppointments(AppointmentFilters filters) {
        return (root, query, cb) -> {
            Join<Appointment, Box> joinTable = root.join(Appointment_.box);
            return cb.and(
                    filterByBox(cb, joinTable, filters.getBoxId()),
                    filterByDate(cb, root, filters.getDate()),
                    filterByTime(cb, root, filters.getTime())
            );
        };
    }

    private static Predicate filterByBox(CriteriaBuilder cb, Join<Appointment, Box> join, String boxId) {
        if (boxId == null) return cb.and();
        return cb.equal(join.get(Box_.ID), boxId);
    }

    private static Predicate filterByDate(CriteriaBuilder cb, Root<Appointment> root, LocalDate date) {
        if (date == null) return cb.and();
        return cb.equal(root.get(Appointment_.DATE), date);
    }

    private static Predicate filterByTime(CriteriaBuilder cb, Root<Appointment> root, LocalTime time) {
        if (time == null) return cb.and();
        return cb.between(cb.literal(time),
                root.get(Appointment_.STARTS_AT),
                root.get(Appointment_.ENDS_AT));
    }

}
