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
import de.msg.terminfindung.common.konstanten.TestProfile;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Basisklasse für Unit-Tests von DAOs. Jeder Test läuft in einer Transaktion ab. Für die Tests wird nur die
 * Persistenzschicht der Anwendung aufgebaut. Zur Bereitstellung von Testdaten kommt DBUnit zum Einsatz, das hier mit
 * Hilfe entsprechender {@link org.springframework.test.context.TestExecutionListeners} konfiguriert wird.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/test-persistence.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@Profile(TestProfile.UNIT_TEST)
@Transactional
public abstract class AbstraktDaoTest {

    @PersistenceContext
    private EntityManager em;

    /**
     * Übermittelt alle vom Entity Manager zwischengespeicherten Operationen an die Datenbank. Dies ist notwendig, damit
     * DBUnit die Datenbank nach dem Test korrekt mit den erwarteten Daten vergleichen kann.
     */
    @After
    public void commit() {
        em.flush();
    }

}
