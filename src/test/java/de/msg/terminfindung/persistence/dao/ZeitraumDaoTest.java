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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.msg.terminfindung.persistence.dao.AbstraktDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.msg.terminfindung.persistence.dao.ZeitraumDao;
import de.msg.terminfindung.persistence.entity.Zeitraum;

public class ZeitraumDaoTest extends AbstraktDaoTest {

    private static final Long ZEITRAUM_ID = 2L;

    @Autowired
    private ZeitraumDao zeitraumDao;

    @Test
    @DatabaseSetup("testZeitraumDaoSetup.xml")
    @ExpectedDatabase(value = "testZeitraumDaoSpeichern.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSpeichern() {
        zeitraumDao.speichere(new Zeitraum("abends"));
    }

    @Test
    @DatabaseSetup("testZeitraumDaoSetup.xml")
    public void testSuchenMitId() {
        Zeitraum zeitraum = zeitraumDao.sucheMitId(ZEITRAUM_ID);

        assertNotNull(zeitraum);
        assertEquals("über Mittag", zeitraum.getBeschreibung());
    }

    @Test
    @DatabaseSetup("testZeitraumDaoSetup.xml")
    @ExpectedDatabase(value = "testZeitraumDaoLoeschen.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testLoesche() {
        Zeitraum zeitraum = zeitraumDao.sucheMitId(ZEITRAUM_ID);
        zeitraumDao.loesche(zeitraum);

        assertNull(zeitraumDao.sucheMitId(ZEITRAUM_ID));
    }

}
