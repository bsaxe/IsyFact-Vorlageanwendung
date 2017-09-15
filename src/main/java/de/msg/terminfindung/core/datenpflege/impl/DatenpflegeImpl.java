package de.msg.terminfindung.core.datenpflege.impl;

import java.time.LocalDate;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.core.datenpflege.Datenpflege;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;

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
    public int loescheVergangeneTerminfindungen(LocalDate stichtag) throws TerminfindungBusinessException {
        return awfVergangeneTermineLoeschen.loescheVergangeneTerminfindungen(stichtag);
    }
}
