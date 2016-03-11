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

import org.springframework.beans.factory.InitializingBean;

import de.msg.terminfindung.core.teilnahme.Teilnahme;
import de.msg.terminfindung.persistence.dao.TeilnehmerDao;
import de.msg.terminfindung.persistence.dao.TeilnehmerZeitraumDao;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.dao.ZeitraumDao;
import de.msg.terminfindung.persistence.entity.Praeferenz;
import de.msg.terminfindung.persistence.entity.Teilnehmer;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;

/**
 * Implementierung der Anwendungskomponente "Teilnahme" zur Teilnahme an Terminfindungen
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
public class TeilnahmeImpl implements Teilnahme, InitializingBean {

	private TerminfindungDao terminfindungDao;
	private TeilnehmerDao teilnehmerDao;
	private ZeitraumDao zeitraumDao;
	private TeilnehmerZeitraumDao teilnehmerZeitraumDao;

	private AwfTermineBestaetigen awfTermineBestaetigen;	
	
	/**
	 * Anwendungsfaelle werden nach IsyFact-Standard initialisiert
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		awfTermineBestaetigen = new AwfTermineBestaetigen();
		awfTermineBestaetigen.setTerminfindungDao(terminfindungDao);
		awfTermineBestaetigen.setTeilnehmerDao(teilnehmerDao);
		awfTermineBestaetigen.setZeitraumDao(zeitraumDao);
		awfTermineBestaetigen.setTeilnehmerZeitraumDao(teilnehmerZeitraumDao);
	}

	/*--------------------------------------------------------------------------
	 *  Die Implementierung der einzelnen fachlichen Methoden geschieht durch
	 *  Aufruf der Methoden in den jeweiligen Anwendungfall-Klassen.
	 *--------------------------------------------------------------------------*/

	@Override
	public void bestaetigeTeilnahme(Terminfindung terminfindung,
			Teilnehmer teilnehmer, Map<Zeitraum, Praeferenz> terminwahl) {
		 
		awfTermineBestaetigen.bestaetigeTeilnahme (terminfindung, teilnehmer, terminwahl);
	}

	/*--------------------------------------------------------------------------
	 *  Getter und Setter
	 *--------------------------------------------------------------------------*/

	public AwfTermineBestaetigen getAwfTermineBestaetigen() {
		return awfTermineBestaetigen;
	}

	public void setAwfTermineBestaetigen(
			AwfTermineBestaetigen awfTermineBestaetigen) {
		this.awfTermineBestaetigen = awfTermineBestaetigen;
	}

	public TerminfindungDao getTerminfindungDao() {
		return terminfindungDao;
	}

	public void setTerminfindungDao(TerminfindungDao terminfindungDao) {
		this.terminfindungDao = terminfindungDao;
	}

	public TeilnehmerDao getTeilnehmerDao() {
		return teilnehmerDao;
	}

	public void setTeilnehmerDao(TeilnehmerDao teilnehmerDao) {
		this.teilnehmerDao = teilnehmerDao;
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
