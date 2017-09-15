package de.msg.terminfindung.gui.util;

import java.time.LocalDateTime;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

public class LocalDateTimeDozerCustomConverter implements CustomConverter {

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue,
        Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof LocalDateTime) {
            return sourceFieldValue;
        } else {
            throw new MappingException(
                "LocalDateTimeDozerCustomConverter used incorrectly. Arguments passed in were:"
                    + existingDestinationFieldValue + " and " + sourceFieldValue);
        }
    }
}
