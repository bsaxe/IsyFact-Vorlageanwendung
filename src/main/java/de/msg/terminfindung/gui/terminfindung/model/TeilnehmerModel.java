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


import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse speichert die Daten eines Teilnehmers in der View-Schicht.
 * Ein Teilnehmer hat eine eindeutige Id, einen Namen und eine Liste
 * von präferierten Zeiträumen (Präferenzen) bzgl. der zur Auswahl stehenden Termine.
 *
 * @author msg systems ag, Dirk Jäger
 */

public class TeilnehmerModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(TeilnehmerModel.class);
	
	private long id;
	private String name = "";
	private List<TeilnehmerZeitraumModel> praeferenzen = new ArrayList<>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TeilnehmerZeitraumModel> getPraeferenzen() {
		return praeferenzen;
	}
	public void setPraeferenzen(List<TeilnehmerZeitraumModel> praeferenzen) {
		this.praeferenzen = praeferenzen;
	}
	
	/**
	 * Gibt die Praeferenz des Teilnehmers für einen bestimmten Zeitraum zurück.
	 * Wenn der Teilnehmer für diesen Zeitraum keine Praeferenz angegeben hat,
	 * oder wenn der übergebene Zeitraum null ist, wird der Wert 0 (=Nein) zurückgegeben.
	 * 
	 * @param zeitraum Der Zeitraum
	 * @return der Praeferenzwert für diesen Zeitraum
	 */
	public PraeferenzModel getPraeferenz(ZeitraumModel zeitraum) {
		
		PraeferenzModel result=PraeferenzModel.NEIN;
		
		if (zeitraum != null) {
			for (TeilnehmerZeitraumModel praeferenz : praeferenzen) {
				
				ZeitraumModel vz = praeferenz.getZeitraum();
				
				if (vz == null) {
					LOG.warn("Zeitraum war null für Präferenz " + praeferenz);
				}
				else {
					if (vz.getId() == zeitraum.getId()) {
						
						result = praeferenz.getPraeferenz();
					}
				}
			}
		}
		return result;
	}
}
