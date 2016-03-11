package de.msg.terminfindung.gui.terminfindung.model;

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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.msg.terminfindung.persistence.entity.Zeitraum;

/**
 * Die Klasse speichert eine Terminfindung in der View-Schicht.
 *
 * @author msg systems ag, Dirk Jäger
 * */

public class ViewTerminfindung implements Serializable{
	
	private static final long serialVersionUID = 1L;

    /**
     * Die Nummer (Id) der Terminfindung
     */
	private long terminfnd_Nr;

    /**
     * Der vom Veranstalter letztlich ausgwählte Termin der Veranstaltung.
     * Dieser Termin (=Zeitraum an einem Tag) wird bei Abschluss der Terminfindung gesetzt.
     */
	private ViewZeitraum defZeitraum;

    /**
     * Die Liste der zur Auwahl stehenden Tage.
     * Jeder Tag enthält mindestens einen Zeitraum an diesem Tag.
     */
	private List<ViewTag> tage = new ArrayList<>();

    /**
     * Die Liste der Teilnehmer.
     */
	private List<ViewTeilnehmer> teilnehmer = new ArrayList<>();

    /**
     * Der Name des Organisators.
     */
    private ViewOrganisator organisator;

    /**
     * Der Name der Veranstaltung.
     */
	private String veranstName = "";

	/**
	 * Sucht in einer ViewTerminfindung nach einem Zeitraum mit der angegebenen Id.
	 * 
	 * @param zeitraumId Die gesuchte Id
	 * @return Der Zeitraum, wenn er in der Terminfindung vorhanden ist, sonst null.
	 */
	public ViewZeitraum findeZeitraumById (long zeitraumId) {
		
		ViewZeitraum result = null;
		if (tage == null) return null;
		
		for (ViewTag t : tage) {
			if (t.getZeitraeume() != null) {
				for (ViewZeitraum z : t.getZeitraeume()) {
					if (z.getZeitraum_nr() == zeitraumId) result=z;
				}
			}
		}
		return result;
	}
	
	/**
	 * Ermittelt, ob in einer Terminfindung ein bestimmter
	 * Teilnehmername bereits vergeben ist.
	 * 
	 * @param name der gesuchte Name
	 * @return true, wenn der Namen bereits für einen Teilnehmer vergeben ist, false wenn nicht.
	 */
	public boolean existsTeilnehmerName (String name) {
		
		if (teilnehmer == null) return false;
		
		for (ViewTeilnehmer tn : teilnehmer) {
			if (tn.getName().equals(name)) return true;
		}	
		return false;
	}
	
	
	/**
	 * Liefert einen Liste aller Zeitraeume der Terminfindung ueber alle Tage.
	 * Die Liste ist in der Reihenfolge der Tage sortiert.
	 * 
	 * @return Liste all Zeitraeume sortiert nach Tagen
	 */
	public List<ViewZeitraum> getAlleZeitraeume () {
		
		if (tage == null) return null;

		List<ViewZeitraum> result = new ArrayList<>();
		
		for (ViewTag t : tage) {
			if (t.getZeitraeume() != null) {
				for (ViewZeitraum z : t.getZeitraeume()) {
					result.add(z);
				}
			}
		}
		return result;
		
	}
	
	/**
	 * Gibt zurück ob eine Terminfindung abgeschlossen ist.
	 * Eine Terminfindung ist genau dann abgeschlossen, wenn ein
	 * definitiver Zeitraum eingetragen ist.
	 * 
	 * @return true, wenn abgeschlossen, sonst false
	 */
	public boolean isAbgeschlossen () {
		
		return defZeitraum!=null;
	}
	
	/* Getter und Setter */
	
	public long getTerminfnd_Nr() {
		return terminfnd_Nr;
	}

	public void setTerminfnd_Nr(long terminfnd_Nr) {
		this.terminfnd_Nr = terminfnd_Nr;
	}

	public ViewZeitraum getDefZeitraum() {
		return defZeitraum;
	}

	public void setDefZeitraum(ViewZeitraum defZeitraum) {
		this.defZeitraum = defZeitraum;
	}

	public List<ViewTag> getTage() {
		return tage;
	}

	public void setTage(List<ViewTag> tage) {
		this.tage= tage;
	}

	public List<ViewTeilnehmer> getTeilnehmer() {
		return teilnehmer;
	}

	public void setTeilnehmer(List<ViewTeilnehmer> teilnehmer) {
		this.teilnehmer = teilnehmer;
	}

	public ViewOrganisator getOrganisator() {
		return organisator;
	}

	public void setOrganisator(ViewOrganisator organisator) {
		this.organisator= organisator;
	}

	public String getVeranstName() {
		return veranstName;
	}

	public void setVeranstName(String veranstName) {
		this.veranstName = veranstName;
	}
	
}
