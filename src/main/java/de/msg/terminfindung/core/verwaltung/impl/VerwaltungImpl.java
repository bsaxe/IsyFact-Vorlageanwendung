package de.msg.terminfindung.core.verwaltung.impl;

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


import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.konstanten.FehlerSchluessel;
import de.msg.terminfindung.core.verwaltung.Verwaltung;
import de.msg.terminfindung.persistence.dao.TeilnehmerDao;
import de.msg.terminfindung.persistence.dao.TeilnehmerZeitraumDao;
import de.msg.terminfindung.persistence.dao.TerminDao;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.dao.ZeitraumDao;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;

/**
 * Interface der Anwendungskomponente "Erstellung" zur Erstellung von Terminfindungen
 *
 * @author msg systems ag, Maximilian Falter
 */

public class VerwaltungImpl implements Verwaltung, InitializingBean {

	private TerminfindungDao terminfindungDao;
	private TeilnehmerDao teilnehmerDao;
	private TerminDao tagDao;
	private ZeitraumDao zeitraumDao;
	private TeilnehmerZeitraumDao teilnehmerZeitraumDao;
	
	private AwfTerminfindungAbschliessen awfTerminfindungAbschliessen;

	private AwfTermineLoeschen awfTermineLoeschen;

	/**
	 * Liest eine Terminfindung mithilfe des Terminfindungs DAO.
	 * @throws TerminfindungBusinessException im Falle einer ungültigen bzw. nicht vorhandener Terminfindunsnummer
	 */
	@Override
	public Terminfindung leseTerminfindung(Long terminfindung_nr) throws TerminfindungBusinessException {
		Terminfindung tf = terminfindungDao.sucheMitId(terminfindung_nr);
		if( tf == null){
			throw new TerminfindungBusinessException(FehlerSchluessel.MSG_TERMINFINDUNG_NICHT_GEFUNDEN, terminfindung_nr.toString());
		}
		return tf; 
	}

	/**
	 * Anwendungsfaelle werden nach IsyFact-Standard initialisiert.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		awfTerminfindungAbschliessen = new AwfTerminfindungAbschliessen();
		awfTerminfindungAbschliessen.setDao(terminfindungDao);
		awfTermineLoeschen = new AwfTermineLoeschen();
		awfTermineLoeschen.setZeitraumDao(zeitraumDao);
		awfTermineLoeschen.setTagDao(tagDao);
		awfTermineLoeschen.setTerminfindungDao(terminfindungDao);
	}	

	/* (non-Javadoc)
	 * @see de.msg.terminfindung.core.erstellung.Erstellung#setzeVeranstaltungstermin(de.msg.terminfindung.persistence.entity.Terminfindung, long)
	 */
	public void setzeVeranstaltungstermin(Terminfindung terminfindung, long zeitraumNr) throws TerminfindungBusinessException {

		awfTerminfindungAbschliessen.setzeVeranstaltungstermin(terminfindung, zeitraumNr);
	}

	/* (non-Javadoc)
	 * @see de.msg.terminfindung.core.erstellung.Erstellung#loescheZeitraum(de.msg.terminfindung.persistence.entity.Zeitraum)
	 */
	public void loescheZeitraeume(Terminfindung terminfindung, List<Zeitraum> zeitraumList) {
		awfTermineLoeschen.loescheZeitraeume(terminfindung, zeitraumList);
	}

	/*--------------------------------------------------------------------------
	 *  Getter und Setter
	 *--------------------------------------------------------------------------*/

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

	public TerminDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TerminDao tagDao) {
		this.tagDao = tagDao;
	}

	public TerminfindungDao getTerminfindungDao() {
		return terminfindungDao;
	}

	public void setTerminfindungDao(TerminfindungDao terminfindungDao) {
		this.terminfindungDao = terminfindungDao;
	}

	public AwfTerminfindungAbschliessen getAwfTerminfindungAbschliessen() {
		return awfTerminfindungAbschliessen;
	}

	public void setAwfTerminfindungAbschliessen(
			AwfTerminfindungAbschliessen awfTerminfindungAbschliessen) {
		this.awfTerminfindungAbschliessen = awfTerminfindungAbschliessen;
	}

	public TeilnehmerZeitraumDao getTeilnehmerZeitraumDao() {
		return teilnehmerZeitraumDao;
	}

	public void setTeilnehmerZeitraumDao(
			TeilnehmerZeitraumDao teilnehmerZeitraumDao) {
		this.teilnehmerZeitraumDao = teilnehmerZeitraumDao;
	}

	public AwfTermineLoeschen getAwfTermineLoeschen() {
		return awfTermineLoeschen;
	}

	public void setAwfTermineLoeschen(AwfTermineLoeschen awfTermineLoeschen) {
		this.awfTermineLoeschen = awfTermineLoeschen;
	}


}
