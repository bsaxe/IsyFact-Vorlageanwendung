package de.msg.terminfindung.gui.util;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import de.bund.bva.isyfact.datetime.format.InFormat;
import de.bund.bva.isyfact.datetime.format.OutFormat;

/**
 * @author Björn Saxe, msg systems ag
 */
public class LocalTimeConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        try {
            return LocalTime.parse(s, InFormat.ZEIT_0H);
        } catch (DateTimeParseException e) {
            throw new ConverterException(s + " ist keine Zeit im Format HH:mm");
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "";
        }

        if (o instanceof LocalTime) {
            return ((LocalTime) o).format(OutFormat.ZEIT_KURZ);
        } else {
            throw new ConverterException(o + " ist keine LocalTime");
        }
    }
}
