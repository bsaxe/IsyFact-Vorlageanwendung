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


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

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
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.core.verwaltung.Verwaltung;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;

/**
 * @author msg systems ag, Maximilian Falter
 */
@ContextConfiguration(locations = {"classpath:spring/test-app-context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("dev")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class TestVerwaltung {

    @Autowired
    private Verwaltung verwaltung;

    /**
     * Test method for {@link de.msg.terminfindung.core.verwaltung.impl.VerwaltungImpl#leseTerminfindung(java.lang.Long)}.
     *
     * @throws TerminfindungBusinessException
     */
    @Test
    public void testLeseTerminfindung() throws TerminfindungBusinessException {

        // Testdaten werde bei der Datenbank-Initialisierung durch JDBC bereitgestellt.
        // Siehe Test-SQL-Skript "test-data.sql" in src/test/resources

        Terminfindung tf = verwaltung.leseTerminfindung(2L);

        assertNotNull(tf);
        assertNotNull(tf.getTermine());
        assert (tf.getTermine().size() == 1);
        List<Zeitraum> zeitraeume = tf.getTermine().get(0).getZeitraeume();
        assert (zeitraeume != null);
        assert (zeitraeume.size() == 1);
        assert (zeitraeume.get(0).getBeschreibung().equals("abends"));
    }

    /**
     * Test method for {@link de.msg.terminfindung.core.erstellung.impl.ErstellungImpl#speichereTerminfindung(de.msg.terminfindung.persistence.entity.Terminfindung)}.
     */
//	@Test
//	public void testErstelleTerminfindung() {
//
//		Terminfindung tf = null;
//		List<Tag> termine = new ArrayList<Tag>();
//		try {
//			Tag t1 = new Tag(dateFormat.parse("2100-01-01"));
//			List<Zeitraum> zeitraeume = new ArrayList<Zeitraum>();
//			zeitraeume.add(new Zeitraum("morgens"));
//			zeitraeume.add(new Zeitraum("abends"));
//			t1.setZeitraeume(zeitraeume);
//
//			Tag t2 = new Tag(dateFormat.parse("2100-01-02"));
//
//			zeitraeume = new ArrayList<Zeitraum>();
//			zeitraeume.add(new Zeitraum("19:00 Uhr - 20:00 Uhr"));
//			zeitraeume.add(new Zeitraum("irgendwann"));
//			t2.setZeitraeume(zeitraeume);
//
//			termine.add(t1);
//			termine.add(t2);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		try {
//			tf = erstellung.erstelleTerminfindung("Max", "Grillfest 2015", termine);
//		} catch (TerminfindungBusinessException e) {
//			e.printStackTrace();
//		}
//		assertNotNull(tf);
//		
//		long tfId = tf.getTerminfnd_Nr();
//		
//		assertTrue(tfId != 0);
//		
//		tf = erstellung.leseTerminfindung(tfId);
//		
//		assertNotNull(tf.getTermine());
//		assert(tf.getTermine().size() == 2);
//		List<Zeitraum> zeitraeume = tf.getTermine().get(0).getZeitraeume();
//		assert(zeitraeume != null);
//		assert (zeitraeume.size() == 2);
//		assert (zeitraeume.get(0).getZeitraum().equals("morgens"));
//	}


    /**
     * Test method for {@link de.msg.terminfindung.core.erstellung.impl.ErstellungImpl#speichereTeilnehmerZeitraum(de.msg.terminfindung.persistence.entity.TeilnehmerZeitraum)}.
     */
//	@Test
//	public void testSpeichereTeilnehmerZeitraum() {
//		Teilnehmer t = new Teilnehmer("Sepp");
//		TeilnehmerZeitraum tz = new TeilnehmerZeitraum(t, (byte) 0);
//		erstellung.speichereTeilnehmerZeitraum(tz);
//		assertTrue(tz.getTeilnehmerZeitraum_Nr()!=0);
//	}

    /**
     * Test method for {@link de.msg.terminfindung.core.erstellung.impl.ErstellungImpl#getTeilnehmerPraeferenzen(de.msg.terminfindung.persistence.entity.Teilnehmer)}.
     */
//	@Test
//	public void testGetTeilnehmerPraeferenzen() {
//		for(Teilnehmer t : erstellung.leseTerminfindung(new Long (2)).getTeilnehmer()){
//			assertTrue(erstellung.getTeilnehmerPraeferenzen(t).size()==1);
//		}
//	}

    /**
     * Test method for {@link de.msg.terminfindung.core.erstellung.impl.ErstellungImpl#loescheZeitraum(de.msg.terminfindung.persistence.entity.Zeitraum)}.
     */
//	@Test
//	public void testLoescheZeitraum() {
//		Zeitraum z = erstellung.leseZeitraum(new Long(5));
//		erstellung.loescheZeitraum(z);
//		assertNull(erstellung.leseZeitraum(new Long(5)));
//	}

    /**
     * Test method for {@link de.msg.terminfindung.core.erstellung.impl.ErstellungImpl#updateTerminfindung(de.msg.terminfindung.persistence.entity.Terminfindung)}.
     */
//	@Test
//	public void testUpdateTerminfindung() {
//		Terminfindung tf = erstellung.leseTerminfindung(new Long(2));
//		assertNull(tf.getDefZeitraum());
//		Zeitraum z = erstellung.leseZeitraum(new Long(5));
//		assertNotNull(z);
//		tf.setDefZeitraum(z);
//		erstellung.updateTerminfindung(tf);
//		assertNotNull(erstellung.leseTerminfindung(new Long(2)).getDefZeitraum());
//	}

    /**
     * Test method for {@link de.msg.terminfindung.core.erstellung.impl.ErstellungImpl#speichereTeilnehmer(de.msg.terminfindung.persistence.entity.Teilnehmer)}.
     */
//	@Test
//	public void testSpeichereTeilnehmer() {
//		Teilnehmer t = new Teilnehmer("Hugo");
//		assertFalse(t.getTeilnehmer_Nr()!=0);
//		erstellung.speichereTeilnehmer(t);
//		assertTrue(t.getTeilnehmer_Nr()!=0);
//	}

    /**
     * Test method for {@link de.msg.terminfindung.core.erstellung.impl.ErstellungImpl#updateZeitraum(de.msg.terminfindung.persistence.entity.Zeitraum)}.
     */
//	@Test
//	public void testUpdateZeitraum() {
//		Zeitraum z = erstellung.leseZeitraum(new Long(5));
//		assertTrue(z.getZeitraum().equals("Abends"));
//		z.setZeitraum("Mittags");
//		erstellung.updateZeitraum(z);
//		assertTrue(erstellung.leseZeitraum(new Long(5)).getZeitraum().equals("Mittags"));
//	}

    /**
     * Test method for {@link de.msg.terminfindung.core.erstellung.impl.ErstellungImpl#leseZeitraum(java.lang.Long)}.
     */
//	@Test
//	public void testLeseZeitraum() {
//		Zeitraum z = erstellung.leseZeitraum(new Long(5));
//		assertNotNull(z);
//	}
}
