package de.msg.terminfindung.persistence.dao.jpa;

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
