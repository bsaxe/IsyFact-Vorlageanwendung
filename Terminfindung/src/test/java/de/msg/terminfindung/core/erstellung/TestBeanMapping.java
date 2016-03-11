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



import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.msg.terminfindung.gui.terminfindung.model.*;
import org.dozer.Mapper;
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

import de.msg.terminfindung.persistence.entity.Teilnehmer;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;

/**
 * @author msg systems ag, Maximilian Falter
 *
 */
@ContextConfiguration(locations = { "classpath:spring/test-app-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@Profile("dev")
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class TestBeanMapping {
	
	@Autowired
	Mapper beanMapper;
	
	@Autowired
	Erstellung erstellung;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	Date DATE1=dateParse("2100-01-01");
	Date DATE2=dateParse("2100-01-02");
	Date DATE3=dateParse("2100-01-03");
	Date DATE4=dateParse("2100-01-04");
	Date DATE5=dateParse("2100-01-05");
	
	@Test
	public void testBeanMapperExists() {

		
		Terminfindung tf = new Terminfindung();
		ViewTerminfindung viewTerminfindung = beanMapper.map(tf, ViewTerminfindung.class);

		// TODO: Komplexere Objektstruktur aufbauen und testen
		
		assertNotNull(viewTerminfindung);
		
	}

	@Test
	public void testMapPersistenceToView() {
		
		// TODO: Implementierung des Tests für das Mapping: Persistence -> View-Modell
		assert(true);
	}
	
	@Test
	public void testMapViewToPersistence() {

		// Erzeuge Terminfindung
		ViewTerminfindung viewTerminfindung = new ViewTerminfindung();

		// Erzeuge Teilnehmer und füge sie zur Terminfindung hinzu
		ViewTeilnehmer viewTeilnehmer1 = new ViewTeilnehmer();
		viewTeilnehmer1.setName("Teilnehmer1");
		ViewTeilnehmer viewTeilnehmer2 = new ViewTeilnehmer();
		viewTeilnehmer2.setName("Teilnehmer2");
		ViewTeilnehmer viewTeilnehmer3 = new ViewTeilnehmer();
		viewTeilnehmer3.setName("Teilnehmer3");

		viewTerminfindung.getTeilnehmer().add(viewTeilnehmer1);
		viewTerminfindung.getTeilnehmer().add(viewTeilnehmer2);
		viewTerminfindung.getTeilnehmer().add(viewTeilnehmer3);

		// Erzeuge Tage und füge sie zur Terminfindung hinzu
		ViewTag viewTag1 = new ViewTag();
		viewTag1.setDatum(DATE1);
		ViewTag viewTag2 = new ViewTag();
		viewTag2.setDatum(DATE2);

		viewTerminfindung.getTage().add(viewTag1);
		viewTerminfindung.getTage().add(viewTag2);

		// Erzeuge Zeitraume und fuege sie den Tagen hinzu 
		ViewZeitraum viewZeitraum1 = (new ViewZeitraum());
		viewZeitraum1.setBeschreibung("Zeitraum1");
		ViewZeitraum viewZeitraum2 = new ViewZeitraum();
		viewZeitraum2.setBeschreibung("Zeitraum2");
		ViewZeitraum viewZeitraum3 = new ViewZeitraum();
		viewZeitraum3.setBeschreibung("Zeitraum3");
		ViewZeitraum viewZeitraum4 = new ViewZeitraum();
		viewZeitraum4.setBeschreibung("Zeitraum4");

		viewTag1.getZeitraeume().add(viewZeitraum1);
		viewTag1.getZeitraeume().add(viewZeitraum2);
		viewTag2.getZeitraeume().add(viewZeitraum3);
		viewTag2.getZeitraeume().add(viewZeitraum4);

		// Erzeuge Präferenzen der Teilnehmer für die Zeiträume und füge sie den Zeiträumen hinzu
		ViewTeilnehmerZeitraum viewPraeferenz1 = new ViewTeilnehmerZeitraum();
		viewPraeferenz1.setTeilnehmer(viewTeilnehmer1);
		viewPraeferenz1.setPraeferenz(ViewPraeferenz.JA);
		ViewTeilnehmerZeitraum viewPraeferenz2 = new ViewTeilnehmerZeitraum();
		viewPraeferenz2.setTeilnehmer(viewTeilnehmer1);
		viewPraeferenz2.setPraeferenz(ViewPraeferenz.WENN_ES_SEIN_MUSS);
		ViewTeilnehmerZeitraum viewPraeferenz3 = new ViewTeilnehmerZeitraum();
		viewPraeferenz3.setTeilnehmer(viewTeilnehmer2);
		viewPraeferenz3.setPraeferenz(ViewPraeferenz.WENN_ES_SEIN_MUSS);
		ViewTeilnehmerZeitraum viewPraeferenz4 = new ViewTeilnehmerZeitraum();
		viewPraeferenz4.setTeilnehmer(viewTeilnehmer2);
		viewPraeferenz4.setPraeferenz(ViewPraeferenz.JA);
		
		viewZeitraum1.getTeilnehmerZeitraeume().add(viewPraeferenz1);
		viewZeitraum2.getTeilnehmerZeitraeume().add(viewPraeferenz2);
		viewZeitraum3.getTeilnehmerZeitraeume().add(viewPraeferenz3);
		viewZeitraum4.getTeilnehmerZeitraeume().add(viewPraeferenz4);
		
		
		// Führe das Mapping durch und teste das Ergebnis
		Terminfindung tf = beanMapper.map(viewTerminfindung, Terminfindung.class);
		
		assertNotNull(tf);
		assertNotNull(tf.getTeilnehmer());
		assertNotNull(tf.getTermine());
		
		List<Teilnehmer> teilnehmer = tf.getTeilnehmer();
		assert(teilnehmer.get(0).getName().equals("Teilnehmer1"));
		assert(teilnehmer.get(1).getName().equals("Teilnehmer2"));
		assert(teilnehmer.get(2).getName().equals("Teilnehmer3"));
		
		List<Tag> tage = tf.getTermine();
		assert(tage.get(0).getZeitraeume().get(0).getTeilnehmerZeitraeume().get(0).getTeilnehmer().getName().equals("Teilnehmer1"));
	}

	private Date dateParse(String str) {

		Date result=null; 
		try {
			result = dateFormat.parse(str);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
