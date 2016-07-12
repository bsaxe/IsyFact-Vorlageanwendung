package de.msg.terminfindung.core.datenpflege.impl;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.core.datenpflege.Datenpflege;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;

import java.util.Date;

/**
 * Implementierung der Anwendungskomponente "Datenpflege" zur Pflege des Datenbestands.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public class DatenpflegeImpl implements Datenpflege {

    private final AwfVergangeneTermineLoeschen awfVergangeneTermineLoeschen;

    public DatenpflegeImpl(TerminfindungDao terminfindungDao) {
        awfVergangeneTermineLoeschen = new AwfVergangeneTermineLoeschen(terminfindungDao);
    }

    @Override
    public void loescheVergangeneTerminfindungen(Date stichtag) throws TerminfindungBusinessException {
        awfVergangeneTermineLoeschen.loescheVergangeneTerminfindungen(stichtag);
    }
}
