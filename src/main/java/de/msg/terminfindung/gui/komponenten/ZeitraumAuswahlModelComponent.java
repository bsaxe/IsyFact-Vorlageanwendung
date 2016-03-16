package de.msg.terminfindung.gui.komponenten;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;
import de.msg.terminfindung.persistence.entity.Praeferenz;

/**
 * Modellkomponente zum Verwalten der Auswahl eines Zeitraums in der GUI.
 * Der Benutzer wählt Zeiträume durch Anklicken einer oder mehrer Checkboxen.
 * Diese Klasse stellt die Datenstruktur bereit, mit der die Checkboxen in JSF hinterlegt werden.
 * Sie wird in mehreren Flows wiederverwendet.
 *
 * @author msg systems ag, Dirk Jäger
 */
public class ZeitraumAuswahlModelComponent implements Serializable {

	private static final long serialVersionUID = 8610872504330422543L;

	private Map<Long,Boolean> checked = new HashMap<>();
	private Map<Long,Integer> checkedRadio = new HashMap<>();
	
	/**
	 * Initialisiert die Datenstruktur für die Checkboxen, mit denen der Benutzer in der GUI Zeiträume auswählt.
	 * Aus der übergebenen Liste mit Tagen werden die Zeiträume ausgelesen und
	 * für jeden Zeitraum wird ein Element in der Datenstruktur angelegt. 
	 * 
	 * @param tage Die Liste der Tage mit den darin enthaltenen Zeiträumen je Tag
	 */
	public void init(List<TagModel> tage) {
			
		for (TagModel tag : tage) {
			for (ZeitraumModel zeitraum : tag.getZeitraeume()) {
				this.getCheckedRadio().put(zeitraum.getZeitraum_nr(), Praeferenz.NEIN.ordinal()); // initialisiere mit 0 = Nein
			}
		}
	}
	
	/* Getter und Setter */
	
	public Map<Long,Boolean> getChecked() {
		return checked;
	}

	public void setChecked(Map<Long,Boolean> checked) {
		this.checked = checked;
	}

	public Map<Long,Integer> getCheckedRadio() {
		return checkedRadio;
	}

	public void setCheckedRadio(Map<Long,Integer> checkedRadio) {
		this.checkedRadio = checkedRadio;
	}
}

