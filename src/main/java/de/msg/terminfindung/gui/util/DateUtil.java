package de.msg.terminfindung.gui.util;

/*
 * #%L
 * Terminfindung
 * %%
 * Copyright (C) 2015 - 2016 Bundesverwaltungsamt (BVA), msg systems ag
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import de.msg.terminfindung.gui.terminfindung.erstellen.ErstellenModel;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;

/**
 * Die Klasse stellt Hilfsmethoden für das Vergleichen und das Handling von Datumswerten bereit
 *
 * @author msg systems, Dirk Jäger
 */
public class DateUtil {

	private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);

	public static boolean isSameDay (TagModel tag, Date date) {
		return (dateFormat.format(tag.getDatum()).equals(dateFormat.format(date)));
	}

	public static boolean containsDay(List<TagModel> list, Date date) {
		for (TagModel tag : list) {
			if (isSameDay(tag, date)) return true;
		}
		return false;
	}

	public static Date convertDate (String dateString) throws ParseException {
		Date tempDate;
		tempDate = dateFormat.parse(dateString);
		return tempDate;
	}

	public static Date getToday () {
		return Calendar.getInstance(Locale.GERMAN).getTime();
	}

	public static Date getYesterday () {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.SECOND, -1);
		return cal.getTime();
	}

	public static Date getNDaysFromToday (int n) {
		Calendar cal = Calendar.getInstance(Locale.GERMAN);
		cal.add(Calendar.DAY_OF_YEAR, n);
		return cal.getTime();
	}

	public static String format (Date date) {
		return dateFormat.format(date);
	}
}
