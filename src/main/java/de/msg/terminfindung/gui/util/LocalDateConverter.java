package de.msg.terminfindung.gui.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import de.bund.bva.isyfact.datetime.format.InFormat;
import de.bund.bva.isyfact.datetime.format.OutFormat;

/**
 * Einfacher Konverter für Datumsangaben
 *
 * @author msg systems ag, Björn Saxe
 */
public class LocalDateConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        try {
            return LocalDate.parse(s, InFormat.DATUM_DD_MM_YYYY);
        } catch (DateTimeParseException e) {
            throw new ConverterException(s + " ist kein gültiges Datum");
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "";
        }

        if (o instanceof LocalDate) {
            return ((LocalDate) o).format(OutFormat.DATUM);
        } else {
            throw new ConverterException(o + " ist kein LocalDate");
        }
    }
}
