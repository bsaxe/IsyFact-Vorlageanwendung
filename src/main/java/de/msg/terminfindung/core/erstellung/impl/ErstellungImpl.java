package de.msg.terminfindung.core.erstellung.impl;

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

import de.msg.terminfindung.persistence.dao.*;
import de.msg.terminfindung.persistence.entity.*;
import org.springframework.beans.factory.InitializingBean;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.core.erstellung.Erstellung;
import de.msg.terminfindung.persistence.entity.Tag;

/**
 * Interface der Anwendungskomponente "Erstellung" zur Erstellung von Terminfindungen
 *
 * @author msg systems ag, Maximilian Falter
 */

public class ErstellungImpl implements Erstellung, InitializingBean {

	private TerminfindungDao terminfindungDao;
	private TeilnehmerDao teilnehmerDao;
	private TerminDao tagDao;
	private ZeitraumDao zeitraumDao;
	private TeilnehmerZeitraumDao teilnehmerZeitraumDao;

	private AwfTerminfindungErstellen awfTerminfindungErstellen;
	private AwfTerminfindungAbschliessen awfTerminfindungAbschliessen;

	private AwfTermineLoeschen awfTermineLoeschen;

	/**
	 * Liest eine Terminfindung mithilfe des Terminfindungs DAO.
	 */
	@Override
	public Terminfindung leseTerminfindung(Long terminfindung_nr) {
		return terminfindungDao.sucheMitId(terminfindung_nr);
	}

	/**
	 * Anwendungsfaelle werden nach IsyFact-Standard initialisiert.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		awfTerminfindungErstellen = new AwfTerminfindungErstellen();
		awfTerminfindungErstellen.setDao(terminfindungDao);
		awfTerminfindungAbschliessen = new AwfTerminfindungAbschliessen();
		awfTerminfindungAbschliessen.setDao(terminfindungDao);
		awfTermineLoeschen = new AwfTermineLoeschen();
		awfTermineLoeschen.setZeitraumDao(zeitraumDao);
		awfTermineLoeschen.setTagDao(tagDao);
		awfTermineLoeschen.setTerminfindungDao(terminfindungDao);
	}
	/*--------------------------------------------------------------------------
	 *  Die Implementierung der einzelnen fachlichen Methoden geschieht durch
	 *  Aufruf der Methoden in den jeweiligen Anwendungfall-Klassen.
	 *--------------------------------------------------------------------------*/

	/* (non-Javadoc)
         * @see de.msg.terminfindung.core.erstellung.Erstellung#erstelleTerminfindung(java.lang.String, java.lang.String, java.util.List)
         */
	public Terminfindung erstelleTerminfindung(String orgName,
											   String veranstName, List<Tag> tagList) throws TerminfindungBusinessException {

		return awfTerminfindungErstellen.erstelleTerminfindung(orgName, veranstName, tagList);

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

	public AwfTerminfindungErstellen getAwfTerminfindungErstellen() {
		return awfTerminfindungErstellen;
	}

	public void setAwfTerminfindungErstellen(
			AwfTerminfindungErstellen awfTerminfindungErstellen) {
		this.awfTerminfindungErstellen = awfTerminfindungErstellen;
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
