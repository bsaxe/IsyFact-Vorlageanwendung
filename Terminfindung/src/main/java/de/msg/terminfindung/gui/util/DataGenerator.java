package de.msg.terminfindung.gui.util;

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


import de.msg.terminfindung.gui.terminfindung.model.ViewTag;
import de.msg.terminfindung.gui.terminfindung.model.ViewTerminfindung;
import de.msg.terminfindung.gui.terminfindung.model.ViewZeitraum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Generierung von Testdaten für manuelle Tests.
 *
 * @author msg systems ag, Dirk Jäger
 */
public class DataGenerator {

    /**
     * Erzeugte eine Dummy-Liste von Tagen mit Zeiträumen.
     * Zum Befüllen des Modells für Testzwecke gedacht.
     *
     * @return Eine fest vorgegebene Test-Liste mit Terminen
     */
    public static List<ViewTag> generateTage()  {

        // Erzeuge eine Liste von drei Tagen
       List<ViewTag> tage = new ArrayList<>();
        for (int i=0; i<=2; i++) {

            ViewTag tag = new ViewTag();
            // Tage beginnem vom aktuellen Datum an
            tag.setDatum(DateUtil.getNDaysFromToday(i));
            tage.add(tag);

            // Erzeuge drei Zeiträume für jeden Tage
            List<ViewZeitraum> zeitraeume = new ArrayList<>();
            for (int j=0; j<=2; j++) {

                ViewZeitraum zeitraum = new ViewZeitraum();
                zeitraum.setBeschreibung("Tag "+ i + ", Zeitraum " + j);
                zeitraeume.add(zeitraum);
            }
            tag.setZeitraeume(zeitraeume);
        }
        return tage;
    }
}
