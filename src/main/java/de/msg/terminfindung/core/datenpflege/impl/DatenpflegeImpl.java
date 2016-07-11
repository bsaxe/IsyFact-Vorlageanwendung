package de.msg.terminfindung.core.datenpflege.impl;

import de.msg.terminfindung.core.datenpflege.Datenpflege;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Terminfindung;

import java.util.Date;

/**
 * Implementierung der Anwendungskomponente "Datenpflege" zur Pflege des Datenbestands.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class DatenpflegeImpl implements Datenpflege {

    private final TerminfindungDao terminfindungDao;

    public DatenpflegeImpl(TerminfindungDao terminfindungDao) {
        this.terminfindungDao = terminfindungDao;
    }

    @Override
    public void loescheVergangeneTerminfindungen(Date loeschFrist) {
        for (Terminfindung tf : terminfindungDao.sucheVor(loeschFrist)) {
            terminfindungDao.loesche(tf);
        }
    }
}
