package de.msg.terminfindung.gui.util;

import java.time.LocalDate;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

public class LocalDateDozerCustomConverter implements CustomConverter {

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue,
        Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof LocalDate) {
            return sourceFieldValue;
        } else {
            throw new MappingException(
                "LocalDateDozerCustomConverter used incorrectly. Arguments passed in were:"
                    + existingDestinationFieldValue + " and " + sourceFieldValue);
        }
    }
}
