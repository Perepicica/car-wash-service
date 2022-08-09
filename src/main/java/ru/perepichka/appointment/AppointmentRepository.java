package ru.perepichka.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String>, JpaSpecificationExecutor<Appointment> {

    @Query(value = "select sum(app.cost) from service_schema.appointment as app " +
                                 "where (app.app_date >= :from and app_date <= :till and status = '1')",
            nativeQuery = true)
    float getRevenue(@Param("from") LocalDate from,
                     @Param("till") LocalDate till);

}
