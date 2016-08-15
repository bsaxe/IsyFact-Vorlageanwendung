package de.msg.terminfindung.gui.pages;

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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class TeilnahmeTabelle {
	private DateTimeFormatter ddMMyyyy = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	private Map<String, List<ZeitraumMitPraeferenz>> zusagen = new HashMap<>();

	private List<Integer> columnSpans = new ArrayList<>();
	private List<LocalDate> tage = new ArrayList<>();
	private List<String> zeitraeume = new ArrayList<>();
	private Map<String, List<Praeferenz>> praeferenzen = new HashMap<>();

	private class ZeitraumMitPraeferenz {
		public LocalDate tag;
		public String zeitraum;
		public Praeferenz praeferenz;

		@Override
		public String toString() {
			return "[tag=" + tag + ", zeitraum=" + zeitraum + ", praeferenz=" + praeferenz + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((praeferenz == null) ? 0 : praeferenz.hashCode());
			result = prime * result + ((tag == null) ? 0 : tag.hashCode());
			result = prime * result + ((zeitraum == null) ? 0 : zeitraum.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ZeitraumMitPraeferenz other = (ZeitraumMitPraeferenz) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (praeferenz != other.praeferenz)
				return false;
			if (tag == null) {
				if (other.tag != null)
					return false;
			} else if (!tag.equals(other.tag))
				return false;
			if (zeitraum == null) {
				if (other.zeitraum != null)
					return false;
			} else if (!zeitraum.equals(other.zeitraum))
				return false;
			return true;
		}

		private TeilnahmeTabelle getOuterType() {
			return TeilnahmeTabelle.this;
		}
	}

	private enum Praeferenz {
		JA, NEIN, WENN_ES_SEIN_MUSS
	};

	private TeilnahmeTabelle() {
	};

	public static TeilnahmeTabelle parseFromHtml(String html) {
		TeilnahmeTabelle tabelle = new TeilnahmeTabelle();
		tabelle.parse(html);
		return tabelle;
	}

	private void parse(String s) {
		Document doc = Jsoup.parse(s);

		Elements rows = doc.select("tr");

		for (Element row : rows) {
			Elements header = row.select("th");

			// Tage und Zeitraeume sind in <th>-Elementen
			for (Element column : header) {
				// Tage: mit colspan Attribut
				if (!column.attr("colspan").isEmpty()) {
					columnSpans.add(Integer.parseInt(column.attr("colspan")));
					tage.add(LocalDate.parse(column.text(), ddMMyyyy));
				}
				// Zeitr�ume ohne colspan
				else if (column.attr("class").startsWith("borderTd ") && column.attr("colspan").isEmpty()) {
					zeitraeume.add(column.text());
				}
			}

			Elements columns = row.select("td");
			// Radio Buttons zur Teilnahme nicht mehr relevant
			if (!columns.isEmpty() && "teilnehmerTd".equals(columns.get(0).attr("class"))) {
				break;
			}

			String neuerTeilnehmer = null;
			// Teilnehmer und Zusagen sind <td>-Elemente
			for (Element column : columns) {
				// Erst kommt in der Zeile der Teilnehmer...
				if (column.attr("class").equals("borderTd") && !column.text().isEmpty()) {
					neuerTeilnehmer = column.text();
				}
				// ...danach seine Zusagen
				else if (column.attr("class").startsWith("borderTd ")) {
					if (praeferenzen.get(neuerTeilnehmer) == null) {
						praeferenzen.put(neuerTeilnehmer, new ArrayList<>());
					}

					switch (column.attr("class")) {
					case "borderTd JA":
						praeferenzen.get(neuerTeilnehmer).add(Praeferenz.JA);
						break;
					case "borderTd NEIN":
						praeferenzen.get(neuerTeilnehmer).add(Praeferenz.NEIN);
						break;
					case "borderTd WENN_ES_SEIN_MUSS":
						praeferenzen.get(neuerTeilnehmer).add(Praeferenz.WENN_ES_SEIN_MUSS);
						break;
					default:
						/* assertTrue(false) */break;
					}
				}
			}
		}

		erstelleZusagen();
	}

	public Set<String> getTeilnehmer() {
		return zusagen.keySet();
	}

	public boolean hatZugesagt(String name, LocalDate tag, String zeitraum) {
		return zusageMitPraeferenz(name, tag, zeitraum, Praeferenz.JA);
	}

	public boolean hatBedingtZugesagt(String name, LocalDate tag, String zeitraum) {
		return zusageMitPraeferenz(name, tag, zeitraum, Praeferenz.WENN_ES_SEIN_MUSS);
	}

	public boolean hatAllesAbgesagt(String name) {
		return zusagen.get(name).isEmpty();
	}

	public boolean enthaeltTagMitZeitraum(LocalDate tag, String zeitraum) {
		for (int i = 0; i < zeitraeume.size(); i++) {
			if (zeitraeume.get(i).equals(zeitraum) && tag.equals(getTagFuerIndex(i))) {
				return true;
			}
		}

		return false;
	}

	private boolean zusageMitPraeferenz(String name, LocalDate tag, String zeitraum, Praeferenz p) {
		List<ZeitraumMitPraeferenz> wuensche = zusagen.get(name);

		if (wuensche == null) {
			return false;
		}

		ZeitraumMitPraeferenz wunsch = new ZeitraumMitPraeferenz();
		wunsch.tag = tag;
		wunsch.zeitraum = zeitraum;
		wunsch.praeferenz = p;

		return wuensche.contains(wunsch);
	}

	private void erstelleZusagen() {
		for (String teilnehmer : praeferenzen.keySet()) {
			zusagen.put(teilnehmer, new ArrayList<>());

			List<Praeferenz> praef = praeferenzen.get(teilnehmer);
			for (int i = 0; i < praef.size(); i++) {
				if (!praef.get(i).equals(Praeferenz.NEIN)) {
					ZeitraumMitPraeferenz terminWunsch = new ZeitraumMitPraeferenz();
					terminWunsch.tag = getTagFuerIndex(i);
					terminWunsch.zeitraum = zeitraeume.get(i);
					terminWunsch.praeferenz = praef.get(i);

					zusagen.get(teilnehmer).add(terminWunsch);
				}
			}
		}
	}

	// Zuordnung der Spaltenindizes der Zeitraumspalten auf deren zugeh�rigen
	// Tag
	private LocalDate getTagFuerIndex(int idx) {
		int tagIdx = 0;
		int colSpan = columnSpans.get(0) - 1;
		while (idx > colSpan) {
			colSpan += columnSpans.get(++tagIdx);
		}

		return tage.get(tagIdx);
	}
}
