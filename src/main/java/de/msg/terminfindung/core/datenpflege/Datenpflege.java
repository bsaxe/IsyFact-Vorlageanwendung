package de.msg.terminfindung.core.datenpflege;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;

import java.util.Date;

/**
 * Schnittstelle der Anwendungskomponente "Datenpflege" zur Pflege der Bestandsdaten.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public interface Datenpflege {

    /**
     * Löscht alle Terminfindungen, die abgeschlossen wurden und deren Termine vor dem angegebenen Stichtag
     * stattgefunden haben.
     *
     * @param stichtag Stichtag
     * @throws TerminfindungBusinessException falls das Datum ungültig ist.
     */
    void loescheVergangeneTerminfindungen(Date stichtag) throws TerminfindungBusinessException;

}
