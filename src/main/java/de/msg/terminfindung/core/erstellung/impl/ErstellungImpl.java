package de.msg.terminfindung.core.erstellung.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.core.erstellung.Erstellung;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;

public class ErstellungImpl implements Erstellung, InitializingBean{

	private TerminfindungDao terminfindungDao;
	private AwfTerminfindungErstellen awfTerminfindungErstellen;
	
	
	/**
	 * Anwendungsfaelle werden nach IsyFact-Standard initialisiert.
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		awfTerminfindungErstellen = new AwfTerminfindungErstellen();
		awfTerminfindungErstellen.setDao(terminfindungDao);
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
	
	public TerminfindungDao getTerminfindungDao() {
		return terminfindungDao;
	}

	public void setTerminfindungDao(TerminfindungDao terminfindungDao) {
		this.terminfindungDao = terminfindungDao;
	}
}
