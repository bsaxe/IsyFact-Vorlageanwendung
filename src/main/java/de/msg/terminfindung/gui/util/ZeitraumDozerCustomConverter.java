package de.msg.terminfindung.gui.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import de.bund.bva.isyfact.datetime.util.DateTimeUtil;
import de.bund.bva.isyfact.datetime.zeitraum.core.Zeitraum;
import de.bund.bva.isyfact.datetime.zeitraum.persistence.ZeitraumEntitaet;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

public class ZeitraumDozerCustomConverter implements CustomConverter {

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue,
        Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof ZeitraumEntitaet) {
            ZeitraumEntitaet zeitraumEntitaet = (ZeitraumEntitaet) sourceFieldValue;

            if (zeitraumEntitaet.getAnfang() == null || zeitraumEntitaet.getEnde() == null) {
                return null;
            }

            if (zeitraumEntitaet.isOhneDatum()) {
                return Zeitraum
                    .of(zeitraumEntitaet.getAnfang().toLocalTime(), zeitraumEntitaet.getEnde().toLocalTime());
            } else {
                return Zeitraum.of(zeitraumEntitaet.getAnfang(), zeitraumEntitaet.getEnde());
            }
        } else if (sourceFieldValue instanceof Zeitraum) {
            Zeitraum zeitraum = (Zeitraum) sourceFieldValue;

            ZeitraumEntitaet zeitraumEntitaet = new ZeitraumEntitaet();
            zeitraumEntitaet.setOhneDatum(zeitraum.isOhneDatum());

            if (zeitraum.isOhneDatum()) {
                LocalDate heute = DateTimeUtil.localDateNow();
                ZoneId UTC = ZoneId.of("UTC");
                zeitraumEntitaet.setAnfang(ZonedDateTime.of(heute, zeitraum.getAnfangszeit(), UTC));
                zeitraumEntitaet.setEnde(ZonedDateTime.of(heute, zeitraum.getEndzeit(), UTC));
            } else {
                zeitraumEntitaet.setAnfang(zeitraum.getAnfangsdatumzeit());
                zeitraumEntitaet.setEnde(zeitraum.getEndedatumzeit());
            }
            return zeitraumEntitaet;
        } else {
            throw new MappingException(
                "ZeitraumDozerCustomConverter used incorrectly. Arguments passed in were:"
                    + existingDestinationFieldValue + " and " + sourceFieldValue);
        }
    }
}
