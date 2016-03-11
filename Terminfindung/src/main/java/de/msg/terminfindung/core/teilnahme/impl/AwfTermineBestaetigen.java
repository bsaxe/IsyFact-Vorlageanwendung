package de.msg.terminfindung.core.teilnahme.impl;

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


import java.util.Map;

import de.msg.terminfindung.persistence.dao.TeilnehmerDao;
import de.msg.terminfindung.persistence.dao.TeilnehmerZeitraumDao;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.dao.ZeitraumDao;
import de.msg.terminfindung.persistence.entity.Praeferenz;
import de.msg.terminfindung.persistence.entity.Teilnehmer;
import de.msg.terminfindung.persistence.entity.TeilnehmerZeitraum;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;

/**
 * @author msg systems ag, Dirk Jäger
 * Diese Klasse implementiert den Anwendungsfall "Termine bestaetigen"
 */
public class AwfTermineBestaetigen {

	private TeilnehmerDao teilnehmerDao;
	private TerminfindungDao terminfindungDao;
	private ZeitraumDao zeitraumDao;
	private TeilnehmerZeitraumDao teilnehmerZeitraumDao;

	public void bestaetigeTeilnahme(Terminfindung terminfindung, Teilnehmer teilnehmer, Map<Zeitraum, Praeferenz> terminwahl) {

		//TODO: Überprüfen, dass Zeiträume aus der Map auch zur Terminfindung gehören

		// Erzeuge den Datensatz für den Teilnehmer
		terminfindung.getTeilnehmer().add(teilnehmer);
		teilnehmerDao.speichere(teilnehmer);
		// terminfindungDao.updateTerminfindung(terminfindung);

		// Iteriere durch die übergebene Map und erzeuge fuer jedes Paar (Zeitraum/Preaferenzwerte) einen Datensatz
		for (Zeitraum zeitraum : terminwahl.keySet()) {

			// Erzeuge die Praeferenzen (Teilnehmer Zeitraum)
			TeilnehmerZeitraum tz = new TeilnehmerZeitraum(teilnehmer, zeitraum, terminwahl.get(zeitraum));
			
			teilnehmerZeitraumDao.speichere(tz);

			// Verbinde die erzeugte Praeferenz mit dem Zeitraum, den sie betrifft
			zeitraum.getTeilnehmerZeitraeume().add(tz);
			// zeitraumDao.updateZeitraum(zeitraum);
		}
		
		terminfindungDao.updateTerminfindung(terminfindung);
	}

	/*------------------------------------------------------------
	 * Getter und Setter
	 *------------------------------------------------------------*/
	
	public TeilnehmerDao getTeilnehmerDao() {
		return teilnehmerDao;
	}

	public void setTeilnehmerDao(TeilnehmerDao teilnehmerDao) {
		this.teilnehmerDao = teilnehmerDao;
	}

	public TerminfindungDao getTerminfindungDao() {
		return terminfindungDao;
	}

	public void setTerminfindungDao(TerminfindungDao terminfindungDao) {
		this.terminfindungDao = terminfindungDao;
	}

	public ZeitraumDao getZeitraumDao() {
		return zeitraumDao;
	}

	public void setZeitraumDao(ZeitraumDao zeitraumDao) {
		this.zeitraumDao = zeitraumDao;
	}

	public TeilnehmerZeitraumDao getTeilnehmerZeitraumDao() {
		return teilnehmerZeitraumDao;
	}

	public void setTeilnehmerZeitraumDao(TeilnehmerZeitraumDao teilnehmerZeitraumDao) {
		this.teilnehmerZeitraumDao = teilnehmerZeitraumDao;
	}

}
