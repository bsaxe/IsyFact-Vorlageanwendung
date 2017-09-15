package de.msg.terminfindung.core.erstellung;

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
import java.util.ArrayList;
import java.util.List;

import de.bund.bva.isyfact.datetime.zeitraum.persistence.ZeitraumEntitaet;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.core.AbstraktCoreTest;
import de.msg.terminfindung.core.erstellung.impl.ErstellungImpl;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * Test für den Anwendungsfall "Terminfindung erstellen".
 *
 * @author msg systems ag, Dirk Jäger
 */
public class ErstellungTest extends AbstraktCoreTest {

    private static final Long TERMINFINDUNG_ID = 4711L;

    private TerminfindungDao terminfindungDao;

    @Before
    public void init() {
        terminfindungDao = mock(TerminfindungDao.class);

        doAnswer(invocation -> {
                tf = invocation.getArgumentAt(0, Terminfindung.class);
                tf.setId(TERMINFINDUNG_ID);
                return null;
        }).when(terminfindungDao).speichere(any(Terminfindung.class));

        doAnswer(invocation -> tf).when(terminfindungDao).sucheMitId(TERMINFINDUNG_ID);
    }

    private Terminfindung tf;

    @Test(expected = TerminfindungBusinessException.class)
    public void erstelleTerminfindungTestOrgNameNull() throws TerminfindungBusinessException {
        Erstellung erstellung = new ErstellungImpl(terminfindungDao);
        erstellung.erstelleTerminfindung(null, "Veranstaltung", new ArrayList<Tag>());
    }

    @Test(expected = TerminfindungBusinessException.class)
    public void erstelleTerminfindungTestVeranstNameNull() throws TerminfindungBusinessException {
        Erstellung erstellung = new ErstellungImpl(terminfindungDao);
        erstellung.erstelleTerminfindung("Organisation", null, new ArrayList<Tag>());
    }

    @Test(expected = TerminfindungBusinessException.class)
    public void erstelleTerminfindungTestTerminListeNull() throws TerminfindungBusinessException {
        Erstellung erstellung = new ErstellungImpl(terminfindungDao);
        erstellung.erstelleTerminfindung("Organisation", "Veranstaltung", null);
    }

    @Test(expected = TerminfindungBusinessException.class)
    public void erstelleTerminfindungTestTerminListeLeer() throws TerminfindungBusinessException {
        Erstellung erstellung = new ErstellungImpl(terminfindungDao);
        erstellung.erstelleTerminfindung("Organisation", "Veranstaltung", new ArrayList<Tag>());
    }

    @Test
    public void testErstelleTerminfindung() throws Exception {
        Erstellung erstellung = new ErstellungImpl(terminfindungDao);

        List<Tag> termine = new ArrayList<>();
        Tag t1 = new Tag(LocalDate.of(2100, 1, 1));
        List<Zeitraum> zeitraeume = new ArrayList<>();
        zeitraeume.add(new Zeitraum(new ZeitraumEntitaet()));
        zeitraeume.add(new Zeitraum(new ZeitraumEntitaet()));
        t1.setZeitraeume(zeitraeume);
        termine.add(t1);

        Tag t2 = new Tag(LocalDate.of(2100, 1, 2));
        zeitraeume = new ArrayList<>();
        zeitraeume.add(new Zeitraum(new ZeitraumEntitaet()));
        zeitraeume.add(new Zeitraum(new ZeitraumEntitaet()));
        t2.setZeitraeume(zeitraeume);
        termine.add(t2);

        Terminfindung terminfindung = erstellung.erstelleTerminfindung("Max", "Grillfest 2015", termine);
        assertNotNull(terminfindung);

        terminfindung = terminfindungDao.sucheMitId(terminfindung.getId());

        assertNotNull(terminfindung.getTermine());
        assertEquals(2, terminfindung.getTermine().size());

        zeitraeume = terminfindung.getTermine().get(0).getZeitraeume();
        assertNotNull(zeitraeume);
        assertEquals(2, zeitraeume.size());
        assertNotNull(zeitraeume.get(0).getZeitraum());
    }
}
