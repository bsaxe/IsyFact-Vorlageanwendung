package de.msg.terminfindung.core.verwaltung;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.bund.bva.isyfact.datetime.zeitraum.persistence.ZeitraumEntitaet;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.core.AbstraktCoreTest;
import de.msg.terminfindung.core.verwaltung.impl.VerwaltungImpl;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author msg systems ag, Maximilian Falter
 */
public class VerwaltungTest extends AbstraktCoreTest {

    private static final UUID TERMINFINDUNG_REF = UUID.fromString("ddec6dd1-4e7e-4e7f-8343-962414a63835");

    private TerminfindungDao terminfindungDao;

    @Before
    public void init() {
        terminfindungDao = mock(TerminfindungDao.class);

        // Terminfindung-DAO-Mock konfigurieren
        Terminfindung muster = new Terminfindung();
        Tag tag = new Tag();
        muster.getTermine().add(tag);
        Zeitraum zeitraum = new Zeitraum();
        zeitraum.setZeitraum(new ZeitraumEntitaet());
        tag.getZeitraeume().add(zeitraum);
        
        Terminfindung muster2 = new Terminfindung();
        tag = new Tag();
        muster2.getTermine().add(tag);
        zeitraum = new Zeitraum();
        zeitraum.setZeitraum(new ZeitraumEntitaet());
        tag.getZeitraeume().add(zeitraum);
        
        List<Terminfindung> alleTermine = new ArrayList<>();
        alleTermine.add(muster);
        alleTermine.add(muster2);

        when(terminfindungDao.sucheMitReferenz("ddec6dd1-4e7e-4e7f-8343-962414a63835")).thenReturn(muster);
        when(terminfindungDao.findeAlle()).thenReturn(alleTermine);
    }

    /**
     * Test method for {@link de.msg.terminfindung.core.verwaltung.impl.VerwaltungImpl#leseTerminfindung(java.util.UUID)}.
     *
     * @throws TerminfindungBusinessException
     */
    @Test
    public void testLeseTerminfindungReferenz() throws TerminfindungBusinessException {
        Verwaltung verwaltung = new VerwaltungImpl(terminfindungDao);

        Terminfindung tf = verwaltung.leseTerminfindung(TERMINFINDUNG_REF);

        assertNotNull(tf);
        assertNotNull(tf.getTermine());
        assertEquals(1, tf.getTermine().size());

        List<Zeitraum> zeitraeume = tf.getTermine().get(0).getZeitraeume();
        assertNotNull(zeitraeume);
        assertEquals(1, zeitraeume.size());
        assertNotNull(zeitraeume.get(0).getZeitraum());
    }

    /**
     * Test method for {@link de.msg.terminfindung.core.verwaltung.impl.VerwaltungImpl#leseAlleTerminfindungen()}.
     *
     */
    @Test
    public void testLeseAlleTerminfindungen() {
    	Verwaltung verwaltung = new VerwaltungImpl(terminfindungDao);
    	
    	List<Terminfindung> alleTerminfindungen = verwaltung.leseAlleTerminfindungen();
    	
    	assertNotNull(alleTerminfindungen);
        assertEquals(2, alleTerminfindungen.size());
        
        List<Zeitraum> zeitraeume = alleTerminfindungen.get(1).getTermine().get(0).getZeitraeume();
        assertNotNull(zeitraeume);
        assertEquals(1, zeitraeume.size());
        assertNotNull(zeitraeume.get(0).getZeitraum());
    }
}
