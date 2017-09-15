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

import java.util.List;
import java.util.UUID;

import de.bund.bva.isyfact.datetime.util.DateTimeUtil;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.konstanten.FehlerSchluessel;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Organisator;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;

/**
 * Diese Klasse implementiert den Anwendungsfall "Terminfindung erstellen"
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */

class AwfTerminfindungErstellen {

    private final TerminfindungDao dao;

    AwfTerminfindungErstellen(TerminfindungDao dao) {
        this.dao = dao;
    }

    /**
     * Erstellt eine neue Terminfindung mit den angegebenen Daten. Die Methode erhält als Eingabeparameter den Namen des
     * Organisators, den Namen der Veranstaltung und eine Liste von Terminen. Die Eingabeparameter dürfen nicht leer
     * sein, andernfalls wird eine fachliche Exception erzeugt.
     *
     * @param organisatorName   Name des Organisators
     * @param veranstaltungName Name der Veranstaltung
     * @param termine           Liste der Termine, die zur Auswahl stehen.
     * @return Die neu erzeugte Terminfindung
     * @throws TerminfindungBusinessException
     */

    Terminfindung erstelleTerminfindung(String organisatorName, String veranstaltungName, List<Tag> termine)
            throws TerminfindungBusinessException {

        // Überprüfe, ob die übergebenen Parameter sinnvolle Werte enthalten

        if (organisatorName == null) {
            throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG, "organisatorName", "null");
        }
        if (veranstaltungName == null) {
            throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG, "veranstaltungName", "null");
        }
        if (termine == null) {
            throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG, "termine", "null");
        }
        if (termine.size() == 0) {
            throw new TerminfindungBusinessException(FehlerSchluessel.MSG_LISTE_LEER, "termine");
        }

        // Lege eine neue Terminfindung an
        Terminfindung terminfindung = new Terminfindung(veranstaltungName, new Organisator(organisatorName));
        bereinigeZeitraeumeInTerminliste(termine);

        terminfindung.setTermine(termine);
        terminfindung.setCreateDate(DateTimeUtil.localDateTimeNow());
        terminfindung.setIdRef(UUID.randomUUID().toString());
        dao.speichere(terminfindung);
        return terminfindung;

    }

    /**
     * Bereinigt die Zeiträume in der übergebenen Liste von Terminen. Leere Zeiträume (null) werden
     * gelöscht. Wenn für einen Tag kein Zeitraum vorhanden ist, wird der gesamte Tag gelöscht.
     *
     * @param termine Die Liste der Termine
     */
    private void bereinigeZeitraeumeInTerminliste(List<Tag> termine) {
        termine.forEach(tag -> tag.getZeitraeume().removeIf(zeitraum -> zeitraum.getZeitraum() == null));
        termine.removeIf(t -> t.getZeitraeume().isEmpty());
    }
}
