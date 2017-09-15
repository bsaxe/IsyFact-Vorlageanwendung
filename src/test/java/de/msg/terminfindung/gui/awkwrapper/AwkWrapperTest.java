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

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.bund.bva.isyfact.datetime.zeitraum.persistence.ZeitraumEntitaet;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.konstanten.TestProfile;
import de.msg.terminfindung.core.erstellung.Erstellung;
import de.msg.terminfindung.core.teilnahme.Teilnahme;
import de.msg.terminfindung.core.verwaltung.Verwaltung;
import de.msg.terminfindung.gui.awkwrapper.impl.AwkWrapperImpl;
import de.msg.terminfindung.gui.terminfindung.model.PraeferenzModel;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.TeilnehmerModel;
import de.msg.terminfindung.gui.terminfindung.model.TerminfindungModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;
import de.msg.terminfindung.persistence.entity.Organisator;
import de.msg.terminfindung.persistence.entity.Praeferenz;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Teilnehmer;
import de.msg.terminfindung.persistence.entity.TeilnehmerZeitraum;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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

    private static final LocalDate DATE1 = LocalDate.of(2100, 1, 1);

    private static final LocalDate DATE2 = LocalDate.of(2100, 1, 2);

    private static final de.bund.bva.isyfact.datetime.zeitraum.core.Zeitraum zeitraum =
        de.bund.bva.isyfact.datetime.zeitraum.core.Zeitraum
            .of(ZonedDateTime.now(), ZonedDateTime.now().plusHours(1));

    @Before
    public void init() throws Exception {
        erstellung = mock(Erstellung.class);
        verwaltung = mock(Verwaltung.class);
        teilnahme = mock(Teilnahme.class);

        // Terminfindung-DAO-Mock konfigurieren
        Terminfindung muster = new Terminfindung();
        Tag tag = new Tag();
        muster.getTermine().add(tag);

        ZeitraumEntitaet zeitraumEntitaet = new ZeitraumEntitaet();
        zeitraumEntitaet.setAnfang(ZonedDateTime.now());
        zeitraumEntitaet.setEnde(ZonedDateTime.now().plusHours(1));

        Zeitraum zeitraum = new Zeitraum();
        zeitraum.setZeitraum(zeitraumEntitaet);
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
        assertNotNull(zeitraeume.get(0).getZeitraum());
    }

    @Test
    public void testBeanMapperViewZuPersistenz() throws TerminfindungBusinessException {
        when(erstellung.erstelleTerminfindung(anyString(), anyString(), anyListOf(Tag.class)))
            .thenAnswer(invocation -> {
                String organisator = invocation.getArgumentAt(0, String.class);
                String veranstaltung = invocation.getArgumentAt(1, String.class);
                List<Tag> termine = invocation.getArgumentAt(2, List.class);
                tf = new Terminfindung(veranstaltung, new Organisator(organisator));
                tf.setTermine(termine);
                return tf;
        });

        when(verwaltung.leseTerminfindung(any(UUID.class))).thenAnswer(invocation -> tf);

        doAnswer(invocation -> {
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
        viewZeitraum1.setZeitraum(zeitraum);
        viewZeitraum1.setId(nextId++);
        ZeitraumModel viewZeitraum2 = new ZeitraumModel();
        viewZeitraum2.setZeitraum(zeitraum);
        viewZeitraum2.setId(nextId++);
        ZeitraumModel viewZeitraum3 = new ZeitraumModel();
        viewZeitraum3.setZeitraum(zeitraum);
        viewZeitraum3.setId(nextId++);
        ZeitraumModel viewZeitraum4 = new ZeitraumModel();
        viewZeitraum4.setZeitraum(zeitraum);
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
        assertEquals(1, tage.get(0).getZeitraeume().get(0).getTeilnehmerZeitraeume().size());

        for (TeilnehmerZeitraum teilnehmerZeitraum : tage.get(0).getZeitraeume().get(0).getTeilnehmerZeitraeume()) {
            assertEquals("Teilnehmer1", teilnehmerZeitraum.getTeilnehmer().getName());
        }
    }
}
