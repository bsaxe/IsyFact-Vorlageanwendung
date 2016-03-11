package de.msg.terminfindung.test.datenzugriff;

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


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import de.msg.terminfindung.persistence.dao.TeilnehmerDao;
import de.msg.terminfindung.persistence.entity.Teilnehmer;
import de.msg.terminfindung.persistence.entity.TeilnehmerZeitraum;

@ContextConfiguration(locations = { "classpath:spring/test-app-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("dev")
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class TestTeilnehmerDao {

	@Autowired
	private TeilnehmerDao tlDAO;
	
	@Test
	@Rollback(true)
	@Transactional
	public void testSpeichern() {
		assertNotNull(tlDAO);
		Teilnehmer tl = new Teilnehmer("Sepp");
		tlDAO.speichere(tl);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testSuchenMitId() {
		assertNotNull(tlDAO);
		Teilnehmer tl = tlDAO.sucheMitId(1200L);
		assertNotNull(tl);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testLoesche() {
		assertNotNull(tlDAO);
		tlDAO.loesche(tlDAO.sucheMitId(1200L));
		Teilnehmer tf = tlDAO.sucheMitId(1200L);
		assertNull(tf);
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testLadePraeferenzZuTeilnehmer() {
		assertNotNull(tlDAO);
		Collection<TeilnehmerZeitraum> list = tlDAO.ladePraeferenzZuTeilnehmer(tlDAO.sucheMitId(1200L));
		assertTrue(list.size()==1);
	}
	
}
