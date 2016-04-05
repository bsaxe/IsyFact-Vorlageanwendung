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


import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.core.AbstraktCoreTest;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * @author msg systems ag, Maximilian Falter
 */
public class VerwaltungTest extends AbstraktCoreTest {

    @Autowired
    private Verwaltung verwaltung;

    @Autowired
    private TerminfindungDao terminfindungDao;

    /**
     * Test method for {@link de.msg.terminfindung.core.verwaltung.impl.VerwaltungImpl#leseTerminfindung(java.lang.Long)}.
     *
     * @throws TerminfindungBusinessException
     */
    @Test
    public void testLeseTerminfindung() throws TerminfindungBusinessException {
        // DAO-Mock konfigurieren
        Terminfindung muster = new Terminfindung();
        Tag tag = new Tag();
        muster.getTermine().add(tag);
        Zeitraum zeitraum = new Zeitraum();
        zeitraum.setBeschreibung("abends");
        tag.getZeitraeume().add(zeitraum);

        when(terminfindungDao.sucheMitId(1L)).thenReturn(muster);

        Terminfindung tf = verwaltung.leseTerminfindung(1L);

        assertNotNull(tf);
        assertNotNull(tf.getTermine());
        assertEquals(1, tf.getTermine().size());

        List<Zeitraum> zeitraeume = tf.getTermine().get(0).getZeitraeume();
        assertNotNull(zeitraeume);
        assertEquals(1, zeitraeume.size());
        assertEquals("abends", zeitraeume.get(0).getBeschreibung());

        reset(terminfindungDao);
    }


}
