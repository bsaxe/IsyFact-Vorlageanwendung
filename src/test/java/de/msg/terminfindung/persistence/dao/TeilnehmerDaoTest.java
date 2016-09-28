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
import de.msg.terminfindung.persistence.entity.Teilnehmer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TeilnehmerDaoTest extends AbstraktDaoTest {

    private static final Long TEILNEHMER_ID = 200L;

    @Autowired
    private TeilnehmerDao teilnehmerDao;

    @Test
    @DatabaseSetup("testTeilnehmerDaoSetup.xml")
    @ExpectedDatabase(value = "testTeilnehmerDaoSpeichern.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSpeichern() {
        teilnehmerDao.speichere(new Teilnehmer("Sepp"));
    }

    @Test
    @DatabaseSetup("testTeilnehmerDaoSetup.xml")
    public void testSuchenMitId() {
        Teilnehmer teilnehmer = teilnehmerDao.sucheMitId(TEILNEHMER_ID);

        assertNotNull(teilnehmer);
        assertEquals("Herbert", teilnehmer.getName());
    }

    @Test
    @DatabaseSetup("testTeilnehmerDaoSetup.xml")
    @ExpectedDatabase(value = "testTeilnehmerDaoLoeschen.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testLoesche() {
        Teilnehmer teilnehmer = teilnehmerDao.sucheMitId(TEILNEHMER_ID);
        teilnehmerDao.loesche(teilnehmer);

        assertNull(teilnehmerDao.sucheMitId(TEILNEHMER_ID));
    }

}
