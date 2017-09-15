package de.msg.terminfindung.gui.util;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import de.bund.bva.isyfact.datetime.format.InFormat;
import de.bund.bva.isyfact.datetime.util.DateTimeUtil;
import de.bund.bva.isyfact.datetime.zeitraum.core.Zeitraum;
import de.bund.bva.pliscommon.konfiguration.common.ReloadableKonfiguration;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;

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

/**
 * Generierung von Testdaten für manuelle Tests.
 *
 * @author msg systems ag, Dirk Jäger
 */
public class DataGenerator {

    /**
     * Erzeugte eine Dummy-Liste von Tagen mit Zeiträumen.
     * Zum Befüllen des Modells für Testzwecke gedacht.
     * @param reloadableKonfiguration 
     *
     * @return Eine fest vorgegebene Test-Liste mit Terminen
     */
    public static List<TagModel> generateTage(ReloadableKonfiguration reloadableKonfiguration)  {
        LocalTime anfangZeit =
            LocalTime.parse(reloadableKonfiguration.getAsString("termin.start.vorgabe"), InFormat.ZEIT_0H);
        LocalTime endeZeit =
            LocalTime.parse(reloadableKonfiguration.getAsString("termin.ende.vorgabe"), InFormat.ZEIT_0H);

        List<TagModel> tage = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            TagModel tag = new TagModel();

            tag.setDatum(DateTimeUtil.localDateNow().plusDays(i));
            tag.setVonZeitraum(anfangZeit);
            tag.setBisZeitraum(endeZeit);

            List<ZeitraumModel> zeiten = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                ZeitraumModel zeitraumModel = new ZeitraumModel();
                zeitraumModel.setZeitraum(Zeitraum.of(LocalTime.of(j + 8, 0), LocalTime.of(j + 9, 0)));
                zeiten.add(zeitraumModel);
            }

            tag.setZeitraeume(zeiten);

            tage.add(tag);
        }

        return tage;
    }

    public static List<LocalTime> getUhrzeitAuswahl() {
        List<LocalTime> zeiten = new ArrayList<>();
        LocalTime zeit = LocalTime.of(23, 45);

        do {
            zeit = zeit.plusMinutes(15);
            zeiten.add(zeit);
        } while (!zeit.equals(LocalTime.of(23, 45)));
        return zeiten;
    }
}
