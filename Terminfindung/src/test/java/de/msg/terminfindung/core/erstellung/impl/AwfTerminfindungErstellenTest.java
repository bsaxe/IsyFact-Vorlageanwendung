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
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Zeitraum;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Test für den Anwendungsfall "Terminfindung erstellen".
 *
 * @author msg systems ag, Dirk Jäger
 */
@ContextConfiguration(locations = { "classpath:spring/test-app-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
//ToDo: Eigenes Testprofil statt "dev" Profil definieren
@Profile("dev")
// @DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// @Transactional
public class AwfTerminfindungErstellenTest {

    //ToDo: Für weitere Tests, DAO mocken und injecten

    List<Tag> termine;
    AwfTerminfindungErstellen awfTerminfindungErstellen = new AwfTerminfindungErstellen();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
    }

    private List<Tag> createDummyListTermine() {

        List<Tag> tList = new ArrayList<>();

        for (int i=0; i<=2; i++) {
            Tag tag = new Tag();
            tList.add(tag);
            tag.setZeitraeume(new ArrayList<Zeitraum>());

            for (int k=0; k<=4; k++) {
                Zeitraum zeitraum = new Zeitraum();
                zeitraum.setTag(tag);
                zeitraum.setZeitraum_Nr(1000*i +k);
                zeitraum.setBeschreibung("Testzeitraum" + zeitraum.getZeitraum_Nr());
                tag.getZeitraeume().add(zeitraum);
            }
        }
        return tList;
    }

    @Test
    public void erstelleTerminfindungTestOrgNameNull()  throws TerminfindungBusinessException {

        exception.expect(TerminfindungBusinessException.class);
        awfTerminfindungErstellen.erstelleTerminfindung(null,"Veranstaltung",termine);
    }

    @Test
    public void erstelleTerminfindungTestVeranstNameNull()  throws TerminfindungBusinessException {

        exception.expect(TerminfindungBusinessException.class);
        awfTerminfindungErstellen.erstelleTerminfindung("Organisation",null,termine);
    }

    @Test
    public void erstelleTerminfindungTestTerminListeNull()  throws TerminfindungBusinessException {

        exception.expect(TerminfindungBusinessException.class);
        awfTerminfindungErstellen.erstelleTerminfindung("Organisation","Veranstaltung",null);
    }

    @Test
    public void erstelleTerminfindungTestTerminListeLeer()  throws TerminfindungBusinessException {

        exception.expect(TerminfindungBusinessException.class);
        awfTerminfindungErstellen.erstelleTerminfindung("Organisation","Veranstaltung",new ArrayList<Tag>());
    }

    @Test
    public void bereinigeZeitraeumeInTerminlisteTest() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Method bereinigeZeitraeumeInTerminliste = AwfTerminfindungErstellen.class.getDeclaredMethod("bereinigeZeitraeumeInTerminliste",new Class[]{List.class});
        bereinigeZeitraeumeInTerminliste.setAccessible(true);

        termine = createDummyListTermine(); // erzeuge einen Testdatensatz

        // Ermittele die Anzahl von Terminen und die Anzahl von Zeiträumen je Tag
        int anzahlTermineVorher = termine.size();
        int[] anzahlZeitraeumeVorher = countZeitraueme(termine);

        // Rufe die Methode auf den Testdaten auf.
        // Erwartung: die Methode ändert die Daten nicht,
        // weil alle Zeiträume eine Beschreibung ungleich dem leeren String ("") haben.
        bereinigeZeitraeumeInTerminliste .invoke(awfTerminfindungErstellen, termine);

        int anzahlTermineNachher = termine.size();
        int[] anzahlZeitraeumeNachher = countZeitraueme(termine);

        assert(anzahlTermineVorher == anzahlTermineNachher);
        for (int i=0; i<anzahlTermineNachher; i++) {
           assert (anzahlZeitraeumeVorher[i] == anzahlZeitraeumeNachher[i]);
        }

        // Setze jetzt die Beschreibungen aller Zeiträume im ersten Tag auf ""
        for (Zeitraum zeitraum : termine.get(0).getZeitraeume()) {
            zeitraum.setBeschreibung("");
        }
        // Setze zusätzlich dei Beschreibung des ersten Zeitraums im zweiten Tag ""
        termine.get(1).getZeitraeume().get(0).setBeschreibung("");

        // Rufe noch einmal die Methode auf
        // Erwartung: der erste Tag wird komplett entfernt, weil er nur Zeiträume mit leerer Beschreibung enthält
        // im zweiten Tag wird nur der erste Zeitraum entfernt.

        bereinigeZeitraeumeInTerminliste .invoke(awfTerminfindungErstellen, termine);

        anzahlTermineNachher = termine.size();

        assert (anzahlTermineNachher == anzahlTermineVorher-1);
        assert (termine.get(0).getZeitraeume().size() == anzahlZeitraeumeVorher[1]-1);
        assert (termine.get(0).getZeitraeume().get(0).getBeschreibung().equals("Testzeitraum1001"));
    }

    /**
     * Zählt die Zeiträume je Tag/Tag in einer Liste von Terminen/Tagen.
     *
     * @param termine die Liste der Termine/Tage
     * @return Ein int[] Array mit der Anzahl der Zeiträume pro Tag
     */
    private int[] countZeitraueme(List<Tag> termine) {
        int[] anzahlZeitraeume = new int[termine.size()];
        for (int i=0; i<termine.size(); i++) {
            anzahlZeitraeume[i]=termine.get(i).getZeitraeume().size();
        }
        return anzahlZeitraeume;
    }
}
