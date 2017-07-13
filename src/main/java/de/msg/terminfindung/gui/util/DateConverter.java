package de.msg.terminfindung.gui.util;

import javax.faces.convert.DateTimeConverter;

/**
 * Einfacher Konverter für Datumsangaben
 *
 * @author msg systems ag, Björn Saxe
 */
public class DateConverter extends DateTimeConverter {
	
	// TODO: read from external properties file?
	private static String formatString = "dd.MM.yyyy";
	
	public DateConverter()
	{
		setPattern(formatString);
	}
}
