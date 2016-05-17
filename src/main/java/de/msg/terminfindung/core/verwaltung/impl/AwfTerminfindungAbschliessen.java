package de.msg.terminfindung.core.verwaltung.impl;

import java.util.Date;

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
import de.msg.terminfindung.common.konstanten.FehlerSchluessel;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;

/**
 * Diese Klasse stellt den Anwendungsfall "Terminfindung abschliessen" dar.
 *
 * @author msg systems ag, Maximilian Falter
 */
class AwfTerminfindungAbschliessen {

    private final TerminfindungDao terminfindungDao;

    AwfTerminfindungAbschliessen(TerminfindungDao terminfindungDao) {
        this.terminfindungDao = terminfindungDao;
    }

    /**
     * Setzt einen Termin aus der Menge der möglichen Termine als den Termin der Veranstaltung. Der Termin wird durch
     * die eindeutige Id des Zeitraums referenziert. Die Methode prüft, ob die Terminfindung tatsächlich diesen Zeitraum
     * als Auswahl enthält und erzeugt eine Exception falls nicht.
     *
     * @param terminfindung Die Terminfindung
     * @param zeitraumNr    Die Id des ausgewählten Zeitraums
     * @throws TerminfindungBusinessException
     */
    void setzeVeranstaltungstermin(Terminfindung terminfindung, long zeitraumNr) throws TerminfindungBusinessException {

        // Parameterüberprüfung
        if (terminfindung == null) {
            throw new TerminfindungBusinessException(FehlerSchluessel.MSG_PARAMETER_UNGUELTIG, "terminfindung", "null");
        }

        // Stelle sicher, dass die Terminfindung einen Zeitraum mit der angegebenen Nummer enthält
        Zeitraum zeitraum = findeZeitraumInTermin(terminfindung, zeitraumNr);
        if (zeitraum == null) {
            throw new TerminfindungBusinessException(FehlerSchluessel.MSG_ZEITRAUM_NICHT_GEFUNDEN, String.valueOf(terminfindung.getId()), String.valueOf(zeitraumNr));
        }

        terminfindung.setDefZeitraum(zeitraum);
        terminfindung.setUpdateDate(new Date());
        terminfindungDao.aktualisiere(terminfindung);
    }

    /**
     * Sucht innerhalb einer Terminfindung einen Zeitraum mit einer bestimmten Zeitraum-Nummer. Wenn ein entsprechender
     * Zeitraum gefunden wird, wird er als Ergebnis zurückgegeben. Wenn nicht, wirft die Methode eine fachliche
     * Exception.
     *
     * @param terminfindung Die Terminfindung, in der nach dem Zeitraum gesucht wird.
     * @param zeitraumNr    Die Nummer des gesuchten Zeitraums.
     * @return Der Zeitraum, wenn er gefunden wird
     */
    Zeitraum findeZeitraumInTermin(Terminfindung terminfindung, long zeitraumNr) {

        if (terminfindung == null) return null;

        Zeitraum result = null;

        for (Tag tag : terminfindung.getTermine()) {
            for (Zeitraum zeitraum : tag.getZeitraeume()) {
                if (zeitraum.getId() == zeitraumNr) {
                    result = zeitraum;
                }
            }
        }
        return result;
    }
}
