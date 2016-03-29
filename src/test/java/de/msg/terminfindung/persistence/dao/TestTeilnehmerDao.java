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


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.msg.terminfindung.common.konstanten.TestProfile;
import de.msg.terminfindung.persistence.entity.Teilnehmer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:spring/test-app-context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Profile(TestProfile.UNIT_TEST)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class TestTeilnehmerDao {

    private static final Long TEILNEHMER_ID = 2L;

    @Autowired
    private TeilnehmerDao teilnehmerDao;

    @Test
    @DatabaseSetup("testTeilnehmerDaoSetup.xml")
    @ExpectedDatabase(value = "testTeilnehmerDaoSpeichernExpected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
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
    public void testLoesche() {
        Teilnehmer teilnehmer = teilnehmerDao.sucheMitId(TEILNEHMER_ID);
        teilnehmerDao.loesche(teilnehmer);

        assertNull(teilnehmerDao.sucheMitId(TEILNEHMER_ID));
    }

}
