package de.msg.terminfindung.persistence.dao;

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


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.msg.terminfindung.persistence.entity.Praeferenz;
import de.msg.terminfindung.persistence.entity.Teilnehmer;
import de.msg.terminfindung.persistence.entity.TeilnehmerZeitraum;
import de.msg.terminfindung.persistence.entity.Zeitraum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TeilnehmerZeitraumDaoTest extends AbstraktDaoTest {

    private static final Long TEILNEHMER_NICHT_ZUGEORDNET = 3L;

    private static final Long ZEITRAUM_MORGENS = 1L;

    private static final Long ZEITRAUM_MITTAGS = 2L;

    private static final Long TEILNEHMER_ZEITRAUM_ID = 4L;

    @Autowired
    private TeilnehmerDao teilnehmerDao;

    @Autowired
    private TerminfindungDao terminfindungDao;

    @Autowired
    private TeilnehmerZeitraumDao tzDao;

    @Test
    @DatabaseSetup("testTeilnehmerZeitraumDaoSetup.xml")
    @ExpectedDatabase(value = "testTeilnehmerZeitraumDaoSpeichern.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSpeichern() {
        Teilnehmer teilnehmer = teilnehmerDao.sucheMitId(TEILNEHMER_NICHT_ZUGEORDNET);

        Zeitraum morgens = sucheZeitraum(ZEITRAUM_MORGENS);
        Zeitraum mittags = sucheZeitraum(ZEITRAUM_MITTAGS);

        tzDao.speichere(new TeilnehmerZeitraum(teilnehmer, morgens, Praeferenz.JA));
        tzDao.speichere(new TeilnehmerZeitraum(teilnehmer, mittags, Praeferenz.WENN_ES_SEIN_MUSS));
    }

    @Test
    @DatabaseSetup("testTeilnehmerZeitraumDaoSetup.xml")
    public void testSuchenMitId() {
        TeilnehmerZeitraum teilnehmerZeitraum = tzDao.sucheMitId(TEILNEHMER_ZEITRAUM_ID);

        assertNotNull(teilnehmerZeitraum);
        assertEquals(Long.valueOf(2L), teilnehmerZeitraum.getTeilnehmer().getId());
        assertEquals(ZEITRAUM_MITTAGS, teilnehmerZeitraum.getZeitraum().getId());
        assertEquals(Praeferenz.JA, teilnehmerZeitraum.getPraeferenz());
    }

    @Test
    @DatabaseSetup("testTeilnehmerZeitraumDaoSetup.xml")
    @ExpectedDatabase(value = "testTeilnehmerZeitraumDaoLoeschen.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testLoesche() {
        TeilnehmerZeitraum teilnehmerZeitraum = tzDao.sucheMitId(TEILNEHMER_ZEITRAUM_ID);
        tzDao.loesche(teilnehmerZeitraum);

        assertNull(tzDao.sucheMitId(TEILNEHMER_ZEITRAUM_ID));
    }

    private Zeitraum sucheZeitraum(Long zeitraumId) {
        for (Zeitraum zeitraum : terminfindungDao.sucheMitId(1L).getTermine().get(0).getZeitraeume()) {
            if (zeitraum.getId().equals(zeitraumId)) {
                return zeitraum;
            }
        }
        return null;
    }

}
