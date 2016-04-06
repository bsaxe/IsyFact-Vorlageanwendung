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


import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.core.AbstraktCoreTest;
import de.msg.terminfindung.core.erstellung.impl.ErstellungImpl;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Test für den Anwendungsfall "Terminfindung erstellen".
 *
 * @author msg systems ag, Dirk Jäger
 */
public class ErstellungTest extends AbstraktCoreTest {

    private static final Long TERMINFINDUNG_ID = 4711L;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private TerminfindungDao terminfindungDao;

    @Before
    public void init() {
        terminfindungDao = mock(TerminfindungDao.class);

        // Terminfindung-DAO-Mock konfigurieren
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                tf = invocation.getArgumentAt(0, Terminfindung.class);
                tf.setTerminfnd_Nr(TERMINFINDUNG_ID);
                return null;
            }
        }).when(terminfindungDao).speichere(any(Terminfindung.class));
        doAnswer(new Answer<Terminfindung>() {
            @Override
            public Terminfindung answer(InvocationOnMock invocation) throws Throwable {
                return tf;
            }
        }).when(terminfindungDao).sucheMitId(TERMINFINDUNG_ID);
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
        Tag t1 = new Tag(DATE_FORMAT.parse("2100-01-01"));
        List<Zeitraum> zeitraeume = new ArrayList<>();
        zeitraeume.add(new Zeitraum("morgens"));
        zeitraeume.add(new Zeitraum("abends"));
        t1.setZeitraeume(zeitraeume);
        termine.add(t1);

        Tag t2 = new Tag(DATE_FORMAT.parse("2100-01-02"));
        zeitraeume = new ArrayList<>();
        zeitraeume.add(new Zeitraum("19:00 Uhr - 20:00 Uhr"));
        zeitraeume.add(new Zeitraum("irgendwann"));
        t2.setZeitraeume(zeitraeume);
        termine.add(t2);

        Terminfindung terminfindung = erstellung.erstelleTerminfindung("Max", "Grillfest 2015", termine);
        assertNotNull(terminfindung);

        long tfId = terminfindung.getTerminfnd_Nr();
        assertTrue(tfId != 0);

        terminfindung = terminfindungDao.sucheMitId(tfId);

        assertNotNull(terminfindung.getTermine());
        assertEquals(2, terminfindung.getTermine().size());

        zeitraeume = terminfindung.getTermine().get(0).getZeitraeume();
        assertNotNull(zeitraeume);
        assertEquals(2, zeitraeume.size());
        assertEquals("morgens", zeitraeume.get(0).getBeschreibung());

        reset(terminfindungDao);
    }
}
