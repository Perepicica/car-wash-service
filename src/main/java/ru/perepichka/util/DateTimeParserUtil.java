package ru.perepichka.util;

import lombok.experimental.UtilityClass;
import ru.perepichka.exception.InvalidDateTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DateTimeParserUtil {

    private static final String TIME_FORMAT = "HH:mm";
    private static final String DATE_FORMAT = "d.MM.yyyy";

    public static LocalTime getLocalTime(String time){
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } catch (DateTimeParseException e){
            throw new InvalidDateTimeException(TIME_FORMAT);
        }
    }

    public static LocalDate getLocalDate(String date){
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException e){
            throw new InvalidDateTimeException(DATE_FORMAT);
        }
    }

}
