package ru.perepichka.box;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BoxRepository extends JpaRepository<Box, String>, JpaSpecificationExecutor<Box> {

    @Query(value ="select service_db.service_schema.box.id, service_db.service_schema.box.opens_at, service_db.service_schema.box.closes_at," +
            "service_db.service_schema.box.work_coefficient, service_db.service_schema.box.name,service_db.service_schema.box.operator_id " +
            "from service_db.service_schema.box "+
                    "except"+
                    "(select service_db.service_schema.box.id, service_db.service_schema.box.opens_at, service_db.service_schema.box.closes_at, " +
                        "service_db.service_schema.box.work_coefficient,service_db.service_schema.box.name,service_db.service_schema.box.operator_id" +
                        " from service_db.service_schema.box "+
                        "inner join service_db.service_schema.appointment as app on service_db.service_schema.box.id = app.box_id"+
                        " where ("+
                            "(app.app_date = :on_date)"+
                            " and "+
                            "app.id <> :id"+
                            " and "+
                            "((CAST(:app_starts_at as TIME) between app.starts_at and app.ends_at)"+
                            "or"+
                            "((CAST(:app_starts_at as TIME) + ((:duration * interval '1 minute') * service_db.service_schema.box.work_coefficient))  between app.starts_at and app.ends_at)"+
                            "or"+
                            "(CAST(:app_starts_at as TIME) <= app.starts_at and (CAST(:app_starts_at as TIME) + ((:duration * interval '1 minute') * service_db.service_schema.box.work_coefficient)) >= app.ends_at)"+
                    ")))"
            ,nativeQuery = true)
    List<Box> getAvailableBoxes(@Param("on_date") LocalDate onDate,
                                @Param("app_starts_at") LocalTime startsAt,
                                @Param("duration") int duration,
                                @Param("id")String appId);
}
