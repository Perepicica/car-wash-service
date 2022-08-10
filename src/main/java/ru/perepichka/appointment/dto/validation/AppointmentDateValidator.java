package ru.perepichka.appointment.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AppointmentDateValidator implements ConstraintValidator<AppointmentDateConstraint, LocalDate> {
    @Override
    public void initialize(AppointmentDateConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        return date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now());
    }
}
