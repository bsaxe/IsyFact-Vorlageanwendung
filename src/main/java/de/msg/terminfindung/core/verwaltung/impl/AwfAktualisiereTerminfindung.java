package de.msg.terminfindung.core.verwaltung.impl;

import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Terminfindung;

/**
 * Die Klasse implementiert den Anwendungsfall Aktualisiere Stammdaten einer Terminfindung
 * @author vadim
 *
 */
public class AwfAktualisiereTerminfindung {

	private final TerminfindungDao terminfindungDao;
	
	AwfAktualisiereTerminfindung(TerminfindungDao terminfindungDao) {
        this.terminfindungDao = terminfindungDao;
    }
	
	public void aktualisiereTerminfindung(Terminfindung terminfindung, String organisatorName, String veranstaltungName){
		terminfindung.setVeranstaltungName(veranstaltungName);
		terminfindung.getOrganisator().setName(organisatorName);
		terminfindungDao.aktualisiere(terminfindung);
	}
}
