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
import de.msg.terminfindung.common.konstanten.TestProfile;
import de.msg.terminfindung.core.erstellung.Erstellung;
import de.msg.terminfindung.core.teilnahme.Teilnahme;
import de.msg.terminfindung.core.verwaltung.Verwaltung;
import de.msg.terminfindung.gui.awkwrapper.impl.AwkWrapperImpl;
import de.msg.terminfindung.gui.terminfindung.model.*;
import de.msg.terminfindung.persistence.entity.*;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.anyListOf;
import static org.mockito.Mockito.anyMapOf;
import static org.mockito.Mockito.*;

/**
 * Testklasse für den AWK-Wrapper. Hier wird hauptsächlich das Bean Mapping zwischen dem View-Model und den Entitäten
 * getestet. Dazu werden Mocks des Anwendungskern erstellt und die Daten, die den Anwendungskern erreichen, mit denen
 * verglichen, die von der GUI aus abgeschickt werden.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/test-awk-wrapper.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@Profile(TestProfile.UNIT_TEST)
public class AwkWrapperTest {

    @Autowired
    private Mapper beanMapper;

    private Erstellung erstellung;

    private Verwaltung verwaltung;

    private Teilnahme teilnahme;

    private Terminfindung tf;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Date DATE1 = parseDate("2100-01-01");
    private static final Date DATE2 = parseDate("2100-01-02");

    @Before
    public void init() throws Exception {
        erstellung = mock(Erstellung.class);
        verwaltung = mock(Verwaltung.class);
        teilnahme = mock(Teilnahme.class);

        // Terminfindung-DAO-Mock konfigurieren
        Terminfindung muster = new Terminfindung();
        Tag tag = new Tag();
        muster.getTermine().add(tag);
        Zeitraum zeitraum = new Zeitraum();
        zeitraum.setBeschreibung("abends");
        tag.getZeitraeume().add(zeitraum);
        when(verwaltung.leseTerminfindung(any(UUID.class))).thenReturn(muster);
    }

    @Test
    public void testBeanMapperPersistenzZuView() throws TerminfindungBusinessException {
        AwkWrapper awkWrapper = new AwkWrapperImpl(erstellung, verwaltung, teilnahme, beanMapper);

        TerminfindungModel terminfindungModel = awkWrapper.ladeTerminfindung(UUID.randomUUID());

        assertNotNull(terminfindungModel);
        assertNotNull(terminfindungModel.getTage());
        assertEquals(1, terminfindungModel.getTage().size());

        List<ZeitraumModel> zeitraeume = terminfindungModel.getTage().get(0).getZeitraeume();
        assertNotNull(zeitraeume);
        assertEquals(1, zeitraeume.size());
        assertEquals("abends", zeitraeume.get(0).getBeschreibung());
    }

    @Test
    public void testBeanMapperViewZuPersistenz() throws TerminfindungBusinessException {
        when(erstellung.erstelleTerminfindung(anyString(), anyString(), anyListOf(Tag.class))).thenAnswer(new Answer<Terminfindung>() {
            @Override
            public Terminfindung answer(InvocationOnMock invocation) throws Throwable {
                String organisator = invocation.getArgumentAt(0, String.class);
                String veranstaltung = invocation.getArgumentAt(1, String.class);
                List<Tag> termine = invocation.getArgumentAt(2, List.class);
                tf = new Terminfindung(veranstaltung, new Organisator(organisator));
                tf.setTermine(termine);
                return tf;
            }
        });
        when(verwaltung.leseTerminfindung(any(UUID.class))).thenAnswer(new Answer<Terminfindung>() {
            @Override
            public Terminfindung answer(InvocationOnMock invocation) throws Throwable {
                return tf;
            }
        });
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Teilnehmer teilnehmer = invocation.getArgumentAt(1, Teilnehmer.class);
                Map<Zeitraum, Praeferenz> praeferenzen = invocation.getArgumentAt(2, Map.class);
                tf.getTeilnehmer().add(teilnehmer);
                for (Zeitraum key : praeferenzen.keySet()) {
                    Praeferenz praeferenz = praeferenzen.get(key);
                    TeilnehmerZeitraum teilnehmerZeitraum = new TeilnehmerZeitraum(teilnehmer, key, praeferenz);
                    for (Tag tag : tf.getTermine()) {
                        for (Zeitraum zeitraum : tag.getZeitraeume()) {
                            if (zeitraum.equals(key)) {
                                zeitraum.getTeilnehmerZeitraeume().add(teilnehmerZeitraum);
                            }
                        }
                    }

                }
                return null;
            }
        }).when(teilnahme).bestaetigeTeilnahme(any(Terminfindung.class), any(Teilnehmer.class), anyMapOf(Zeitraum.class, Praeferenz.class));

        AwkWrapper awkWrapper = new AwkWrapperImpl(erstellung, verwaltung, teilnahme, beanMapper);

        Long nextId = 1L;

        // Erzeuge Tage und füge sie zur Terminfindung hinzu
        List<TagModel> termine = new ArrayList<>();
        TagModel viewTag1 = new TagModel();
        viewTag1.setDatum(DATE1);
        viewTag1.setId(nextId++);
        TagModel viewTag2 = new TagModel();
        viewTag2.setDatum(DATE2);
        viewTag2.setId(nextId++);

        termine.add(viewTag1);
        termine.add(viewTag2);

        // Erzeuge Zeitraume und fuege sie den Tagen hinzu
        ZeitraumModel viewZeitraum1 = (new ZeitraumModel());
        viewZeitraum1.setBeschreibung("Zeitraum1");
        viewZeitraum1.setId(nextId++);
        ZeitraumModel viewZeitraum2 = new ZeitraumModel();
        viewZeitraum2.setBeschreibung("Zeitraum2");
        viewZeitraum2.setId(nextId++);
        ZeitraumModel viewZeitraum3 = new ZeitraumModel();
        viewZeitraum3.setBeschreibung("Zeitraum3");
        viewZeitraum3.setId(nextId++);
        ZeitraumModel viewZeitraum4 = new ZeitraumModel();
        viewZeitraum4.setBeschreibung("Zeitraum4");
        viewZeitraum4.setId(nextId++);

        viewTag1.getZeitraeume().add(viewZeitraum1);
        viewTag1.getZeitraeume().add(viewZeitraum2);
        viewTag2.getZeitraeume().add(viewZeitraum3);
        viewTag2.getZeitraeume().add(viewZeitraum4);

        // Erzeuge Terminfindung initial
        TerminfindungModel tfModel = awkWrapper.erstelleTerminfindung("Heinz", "Grillfest 2017", termine);


        // Erzeuge Teilnehmer und füge sie zur Terminfindung hinzu
        TeilnehmerModel viewTeilnehmer1 = new TeilnehmerModel(nextId++, "Teilnehmer1");
        TeilnehmerModel viewTeilnehmer2 = new TeilnehmerModel(nextId++, "Teilnehmer2");
        TeilnehmerModel viewTeilnehmer3 = new TeilnehmerModel(nextId++, "Teilnehmer3");

        // Erzeuge Präferenzen der Teilnehmer für die Zeiträume und füge sie den Zeiträumen hinzu
        Map<ZeitraumModel, PraeferenzModel> praeferenz = new HashMap<>();
        praeferenz.put(viewZeitraum1, PraeferenzModel.JA);
        praeferenz.put(viewZeitraum2, PraeferenzModel.WENN_ES_SEIN_MUSS);

        tfModel = awkWrapper.bestaetigeTeilnahme(tfModel, viewTeilnehmer1, praeferenz);

        praeferenz = new HashMap<>();
        praeferenz.put(viewZeitraum3, PraeferenzModel.WENN_ES_SEIN_MUSS);
        praeferenz.put(viewZeitraum4, PraeferenzModel.JA);

        tfModel = awkWrapper.bestaetigeTeilnahme(tfModel, viewTeilnehmer2, praeferenz);

        praeferenz = new HashMap<>();
        awkWrapper.bestaetigeTeilnahme(tfModel, viewTeilnehmer3, praeferenz);

        assertNotNull(tf);
        assertNotNull(tf.getTeilnehmer());
        assertNotNull(tf.getTermine());

        List<Teilnehmer> teilnehmer = tf.getTeilnehmer();
        assertEquals("Teilnehmer1", teilnehmer.get(0).getName());
        assertEquals("Teilnehmer2", teilnehmer.get(1).getName());
        assertEquals("Teilnehmer3", teilnehmer.get(2).getName());

        List<Tag> tage = tf.getTermine();
        assertEquals("Teilnehmer1", tage.get(0).getZeitraeume().get(0).getTeilnehmerZeitraeume().get(0).getTeilnehmer().getName());
    }

    private static Date parseDate(String dateAsString) {

        try {
            return dateFormat.parse(dateAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
