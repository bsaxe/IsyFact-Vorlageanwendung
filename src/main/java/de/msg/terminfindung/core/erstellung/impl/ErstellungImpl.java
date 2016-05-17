package de.msg.terminfindung.core.erstellung.impl;

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

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.core.erstellung.Erstellung;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;

import java.util.List;

/**
 * Implementiert die Schnittstelle {@link Erstellung}. Die Implementierung der einzelnen fachlichen Methoden geschieht
 * durch Aufruf der Methoden in den jeweiligen Anwendungsfall-Klassen.
 */
public class ErstellungImpl implements Erstellung {

    private final AwfTerminfindungErstellen awfTerminfindungErstellen;

    public ErstellungImpl(TerminfindungDao terminfindungDao) {
        awfTerminfindungErstellen = new AwfTerminfindungErstellen(terminfindungDao);
    }


    @Override
    public Terminfindung erstelleTerminfindung(String organisatorName, String veranstaltungName, List<Tag> termine)
            throws TerminfindungBusinessException {

        return awfTerminfindungErstellen.erstelleTerminfindung(organisatorName, veranstaltungName, termine);

    }

}
