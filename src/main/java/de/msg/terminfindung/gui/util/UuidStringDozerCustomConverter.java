package de.msg.terminfindung.gui.util;

import java.util.UUID;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

public class UuidStringDozerCustomConverter implements CustomConverter {

	@Override
	public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		if (sourceFieldValue == null) {
			return null;
		}

		// String -> UUID
		if (sourceFieldValue instanceof String) {
			try {
				return convertStringToUUID((String) sourceFieldValue);
			} catch (IllegalArgumentException e) {
				throw new MappingException(e.getMessage());
			}
		// UUID -> String
		} else if (sourceFieldValue instanceof UUID) {
			return convertUUIDtoString((UUID) sourceFieldValue);
		} else {
			throw new MappingException("UuidStringDozerCustomConverter used incorrectly. Arguments passed in were:"
									   + existingDestinationFieldValue + " and " + sourceFieldValue);
		}
	}

	private UUID convertStringToUUID(String s) {
		return UUID.fromString(s);
	}

	private String convertUUIDtoString(UUID u) {
		return u.toString();
	}
}
