package de.msg.terminfindung.persistence.dao.jpa;

import de.bund.bva.pliscommon.persistence.dao.AbstractDao;
import de.msg.terminfindung.persistence.dao.AbstraktDao;
import de.msg.terminfindung.persistence.entity.AbstraktEntitaet;

/**
 * Abstrakte Basisklasse für JPA-basierte DAOs. Fügt gegenüber der abstrakten Klasse aus plis-persistence eine Methode
 * zum Aktualisieren der Entität hinzu und legt den Typ des Primärschlüssels auf {@link Long} fest.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public abstract class AbstraktJpaDao<T extends AbstraktEntitaet> extends AbstractDao<T, Long> implements AbstraktDao<T> {

    @Override
    public T aktualisiere(T entitaet) {
        return getEntityManager().merge(entitaet);
    }

}
