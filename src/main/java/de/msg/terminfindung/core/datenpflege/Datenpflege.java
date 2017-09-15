package de.msg.terminfindung.core.datenpflege;

import java.time.LocalDate;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;

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
     * @return Anzahl der gelöschten Terminfindungen.
     * @throws TerminfindungBusinessException falls das Datum ungültig ist.
     */
    int loescheVergangeneTerminfindungen(LocalDate stichtag) throws TerminfindungBusinessException;

}
