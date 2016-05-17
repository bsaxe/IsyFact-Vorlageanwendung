package de.msg.terminfindung.core.verwaltung.impl;

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

import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Diese Klasse implementiert den Anwendungsfall "Termine loeschen"
 *
 * @author msg systems ag, Dirk Jäger
 */
class AwfTermineLoeschen {

    private final TerminfindungDao terminfindungDao;

    AwfTermineLoeschen(TerminfindungDao terminfindungDao) {
        this.terminfindungDao = terminfindungDao;
    }

    /**
     * Löscht eine Liste von Zeiträumen aus einer Terminfindung. Die Methode prüft ob durch das Löschen leere Tage (ohne
     * zugeordnete Zeiträume) entstehen und löscht diese dann ebenfalls
     *
     * @param terminfindung Die Terminfindung
     * @param zeitraumList  Die Liste der Zeiträume
     */
    void loescheZeitraeume(Terminfindung terminfindung,
                           List<Zeitraum> zeitraumList) {

    	boolean deleted = false;
        // Lösche die Zeiträume aus der Liste
        for (Tag tag : terminfindung.getTermine()) {
            Iterator<Zeitraum> zeitraeume = tag.getZeitraeume().iterator();
            while (zeitraeume.hasNext()) {
                Zeitraum zeitraum = zeitraeume.next();
                if (zeitraumList.contains(zeitraum)) {
                    zeitraeume.remove();
                    deleted = true;
                }
            }
        }

        // Falls nach dem Löschen ein Tag keine Zeiträume mehr hat, wird der Tag gelöscht.
        Iterator<Tag> tagIterator = terminfindung.getTermine().iterator();
        while (tagIterator.hasNext()) {
            Tag tag = tagIterator.next();
            if (tag.getZeitraeume().isEmpty()) {
                tagIterator.remove();
            }
        }

        if(deleted){
        	terminfindung.setUpdateDate(new Date());
        }
        terminfindungDao.aktualisiere(terminfindung);
    }

}
