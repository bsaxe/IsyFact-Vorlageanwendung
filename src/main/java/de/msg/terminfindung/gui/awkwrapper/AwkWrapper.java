package de.msg.terminfindung.gui.awkwrapper;

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
import de.msg.terminfindung.gui.terminfindung.model.*;

import java.util.List;
import java.util.Map;

/**
 * Interface des Anwendungskern-Wrappers (AWK-Wrapper). Der AWK-Wrapper kapselt den Anwendungskern und stellt Methoden
 * zur Verfügung, die auf View-Objekten arbeiten. In der Implementierung es AWK-Wrappers werden die View-Objekte in
 * Objekte den Anwendungskerns übersetzt, und mit diesen Objekten werden dann die Methoden des Anwendungskerns
 * aufgerufen. Ziel ist die Entkopplung der Datenmodelle des Views und des Anwendungskerns.
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
public interface AwkWrapper {

    /**
     * Laden einer Terminfindung anhand der Terminfindungsnummer.
     *
     * @param terminfindungsNr Die Id einer Terminfindung
     * @return die Terminfindung falls diese existiert, sonst null
     * @throws TerminfindungBusinessException im Falle einer ungültigen bzw. nicht vorhandener Terminfindungsnummer
     */
    TerminfindungModel ladeTerminfindung(long terminfindungsNr) throws TerminfindungBusinessException;

    /**
     * Erstellt eine neue Terminfindung.
     *
     * @param organisatorName   Name des Organisators
     * @param veranstaltungName Name der Veranstaltung
     * @param tage              Eine Liste der Tage (mit jeweils angefügten Zeiträumen) für die die Teilnehmer ihre
     *                          Präferenzen eingeben sollen.
     * @return Die Terminfindung
     * @throws TerminfindungBusinessException Wenn die Terminfindung nicht erstellt werden kann, weil z.B. notwendige
     *                                        Parameter fehlen.
     */
    TerminfindungModel erstelleTerminfindung(String organisatorName, String veranstaltungName, List<TagModel> tage) throws TerminfindungBusinessException;

    /**
     * Setzt den definitiven Veranstaltungstermin und schließt damit eine Terminfindung ab.
     *
     * @param terminfindungModel Die Terminfindung
     * @param zeitraumNr         die Id des Zeitraums, an dem die Veranstaltung stattfindet (Ein Tag ist ein Zeitraum an
     *                           einem bestimmten Tag)
     * @return Die aktualisierte Terminfindung
     * @throws TerminfindungBusinessException Wenn die Terminfindung nicht abgeschlossen werden konnte, oder der
     *                                        Zeitraum nicht in der Terminfindung enthalten ist.
     */
    TerminfindungModel setzeVeranstaltungstermin(TerminfindungModel terminfindungModel, long zeitraumNr) throws TerminfindungBusinessException;

    /**
     * Trägt einen neuen Teilnehmer in mit den angegebenen Präferenzen in eine Terminfindung ein. Die Präferenzen werden
     * in einer Map<ZeitraumModel,PraeferenzModel> übergeben. Für jeden Zeitraum der Terminfindung enthält die Map den
     * Präferenzwert. Die Methode wirft ein Exception, wenn ein Zeitraum aus der Map nicht teil der übergebenen
     * Terminfindung ist.
     *
     * @param terminfindungModel Die Terminfindung
     * @param teilnehmerModel    Der neu einzutragende Teilnehmer
     * @param terminwahl         Die Präferenzen des Teilnehmers
     * @return Die aktualisierte Terminfindung
     * @throws TerminfindungBusinessException
     */
    TerminfindungModel bestaetigeTeilnahme(TerminfindungModel terminfindungModel, TeilnehmerModel teilnehmerModel, Map<ZeitraumModel, PraeferenzModel> terminwahl) throws TerminfindungBusinessException;

    /**
     * Löscht einen Zeitraum aus einer Terminfindung. Alle bereits für den Tag eingetragenen Präferenzen von Teilnehmern
     * werden damit ebenfalls gelöscht.
     *
     * @param terminfindungModel Die Terminfindung
     * @param viewZeitraumList   Eine List der zu löschende Zeiträume
     * @return Die aktualisierte Terminfindung
     * @throws TerminfindungBusinessException
     */
    TerminfindungModel loescheZeitraeume(TerminfindungModel terminfindungModel, List<ZeitraumModel> viewZeitraumList) throws TerminfindungBusinessException;
}
