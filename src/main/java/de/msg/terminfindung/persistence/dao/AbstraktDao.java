package de.msg.terminfindung.persistence.dao;

import de.bund.bva.pliscommon.persistence.dao.Dao;

/**
 * Basis-Interface alle DAOs der Anwendung. Fügt gegenüber des Interfaces aus plis-persistence eine Methode zum
 * Aktualisieren der Entität hinzu und legt den Typ des Primärschlüssels auf {@link Long} fest.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public interface AbstraktDao<T> extends Dao<T, Long> {

    /**
     * Aktualisiert eine veränderte, zurvor persistierte Entität.
     *
     * @param entitaet Entität
     * @return die aktualisierte Entität.
     */
    T aktualisiere(T entitaet);

}
