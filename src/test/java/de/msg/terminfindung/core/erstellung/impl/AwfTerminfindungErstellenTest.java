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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.bund.bva.isyfact.datetime.zeitraum.persistence.ZeitraumEntitaet;
import de.msg.terminfindung.core.AbstraktCoreTest;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Zeitraum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test für den Anwendungsfall "Terminfindung erstellen".
 *
 * @author msg systems ag, Dirk Jäger
 */
public class AwfTerminfindungErstellenTest extends AbstraktCoreTest {

    @Test
    public void bereinigeZeitraeumeInTerminlisteTest() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        AwfTerminfindungErstellen awfTerminfindungErstellen = new AwfTerminfindungErstellen(null);

        Method bereinigeZeitraeumeInTerminliste = AwfTerminfindungErstellen.class.getDeclaredMethod("bereinigeZeitraeumeInTerminliste", new Class[]{List.class});
        bereinigeZeitraeumeInTerminliste.setAccessible(true);

        List<Tag> termine = createDummyListTermine(); // erzeuge einen Testdatensatz

        // Ermittle die Anzahl von Terminen und die Anzahl von Zeiträumen je Tag
        int anzahlTermineVorher = termine.size();
        int[] anzahlZeitraeumeVorher = countZeitraueme(termine);

        // Rufe die Methode auf den Testdaten auf.
        // Erwartung: die Methode ändert die Daten nicht,
        // weil alle Zeiträume eine intern nicht null sind.
        bereinigeZeitraeumeInTerminliste.invoke(awfTerminfindungErstellen, termine);

        int anzahlTermineNachher = termine.size();
        int[] anzahlZeitraeumeNachher = countZeitraueme(termine);

        assert (anzahlTermineVorher == anzahlTermineNachher);
        for (int i = 0; i < anzahlTermineNachher; i++) {
            assert (anzahlZeitraeumeVorher[i] == anzahlZeitraeumeNachher[i]);
        }

        // Setze jetzt die internen Zeiträume aller Zeiträume im ersten Tag auf null
        for (Zeitraum zeitraum : termine.get(0).getZeitraeume()) {
            zeitraum.setZeitraum(null);
        }
        // Setze zusätzlich die Beschreibung des ersten Zeitraums im zweiten Tag auf null
        termine.get(1).getZeitraeume().get(0).setZeitraum(null);

        // Rufe noch einmal die Methode auf
        // Erwartung: der erste Tag wird komplett entfernt, weil er nur Zeiträume mit leerer Beschreibung enthält
        // im zweiten Tag wird nur der erste Zeitraum entfernt.
        bereinigeZeitraeumeInTerminliste.invoke(awfTerminfindungErstellen, termine);

        anzahlTermineNachher = termine.size();

        assertEquals(anzahlTermineNachher, anzahlTermineVorher - 1);
        assertEquals(termine.get(0).getZeitraeume().size(), anzahlZeitraeumeVorher[1] - 1);
        assertNotNull(termine.get(0).getZeitraeume().get(0).getZeitraum());
    }

    private List<Tag> createDummyListTermine() {
        List<Tag> tList = new ArrayList<>();

        for (int i = 0; i <= 2; i++) {
            Tag tag = new Tag();
            tList.add(tag);
            tag.setZeitraeume(new ArrayList<Zeitraum>());

            for (int k = 0; k <= 4; k++) {
                Zeitraum zeitraum = new Zeitraum();
                zeitraum.setId(1000L * i + k);
                zeitraum.setZeitraum(new ZeitraumEntitaet());
                tag.getZeitraeume().add(zeitraum);
            }
        }
        return tList;
    }


    /**
     * Zählt die Zeiträume je Tag/Tag in einer Liste von Terminen/Tagen.
     *
     * @param termine die Liste der Termine/Tage
     * @return Ein int[] Array mit der Anzahl der Zeiträume pro Tag
     */
    private int[] countZeitraueme(List<Tag> termine) {
        int[] anzahlZeitraeume = new int[termine.size()];
        for (int i = 0; i < termine.size(); i++) {
            anzahlZeitraeume[i] = termine.get(i).getZeitraeume().size();
        }
        return anzahlZeitraeume;
    }

}
