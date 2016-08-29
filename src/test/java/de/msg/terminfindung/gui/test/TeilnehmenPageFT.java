package de.msg.terminfindung.gui.test;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import de.msg.terminfindung.gui.pages.ErstellenPage;
import de.msg.terminfindung.gui.pages.TeilnehmenPage;
import de.msg.terminfindung.gui.pages.VerwaltenPage;

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

@RunWith(Arquillian.class)
@RunAsClient
public class TeilnehmenPageFT {

	@Page
	private TeilnehmenPage teilnehmenPage;

	@Page
	private ErstellenPage erstellenPage;
	
	@Page
	private VerwaltenPage verwaltenPage;

	@Test
	public void zeigtDatenDerTerminfindungAn() {
		teilnehmenPage.ladeBestehendeTerminfindungImBrowser(erstelleTerminfindung("Test zeigt Daten an", "Tester"));
		
		assertTrue(teilnehmenPage.zeigtNameUndOrganisator("Test zeigt Daten an", "Tester"));
	}

	@Test
	public void keineTeilnahmeMehrAnAbgeschlossenerTerminfindungMoeglich() {
		String ref = erstelleTerminfindung("Test abgeschlossene Terminfindung", "Tester");
		
		verwaltenPage.ladeBestehendeTerminfindungImBrowser(ref);
		verwaltenPage.schliesseTerminfindungAb(0);
		
		teilnehmenPage.ladeBestehendeTerminfindungImBrowser(ref);
		
		assertTrue(teilnehmenPage.teilnahmeButtonsSindDeaktiviert());
	}

	@Test
	public void teilnehmerHinzufuegen() {
		String veranstaltungName = "Test neuer Teilnehmer";
		String organisator = "Tester";
		List<LocalDate> tage = Arrays.asList(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(3));
		List<LocalTime> zeitenVon = Arrays.asList(LocalTime.of(9, 15), LocalTime.of(11, 45), LocalTime.of(14, 30));
		List<LocalTime> zeitenBis = Arrays.asList(LocalTime.of(11, 30), LocalTime.of(13, 0), LocalTime.of(16, 0));

		erstellenPage.imBrowserGeoeffnet();
		String terminfindungsRef = erstellenPage.terminfindungErstelltMitZeiten(veranstaltungName, organisator, tage,
				zeitenVon, zeitenBis);
		teilnehmenPage.ladeBestehendeTerminfindungImBrowser(terminfindungsRef);

		teilnehmenPage.neuerTerminwunsch(0);
		teilnehmenPage.neuerTeilnehmer("Klaus");
		teilnehmenPage.neuerTerminwunsch(0, 12);
		teilnehmenPage.neuerTeilnehmer("Maria");
		teilnehmenPage.neuerTerminwunsch(0, 3, 6);
		teilnehmenPage.neuerTeilnehmerMitAbsage("Peter");

		assertTrue(teilnehmenPage.zeigtTeilnehmerMitZusageFuerTagUndZeitraum("Klaus", tage.get(0), "09:15 - 11:30"));
		assertTrue(teilnehmenPage.zeigtTeilnehmerMitZusageFuerTagUndZeitraum("Maria", tage.get(0), "09:15 - 11:30"));
		assertTrue(teilnehmenPage.zeigtTeilnehmerMitZusageFuerTagUndZeitraum("Maria", tage.get(1), "11:45 - 13:00"));
		assertTrue(teilnehmenPage.zeigtTeilnehmerKannNicht("Peter"));
	}

	@Test
	public void teilnahmeOhneNameNichtMoeglich() {
		teilnehmenPage.ladeBestehendeTerminfindungImBrowser(erstelleTerminfindung("Test Teilnahme ohne Name", "Tester"));
		
		teilnehmenPage.neuerTeilnehmer("");
		assertTrue(teilnehmenPage.zeigtTooltipMitFehlertext("Bitte Namen eingeben (NA)"));

		teilnehmenPage.neuerTeilnehmerMitAbsage("");
		assertTrue(teilnehmenPage.zeigtTooltipMitFehlertext("Bitte Namen eingeben (NA)"));
	}
	
	@Test
	public void teilnahmeTeilnehmerExistiertBereits()
	{
		teilnehmenPage.ladeBestehendeTerminfindungImBrowser(erstelleTerminfindung("Test Teilnahme Teilnehmer existiert bereits", "Tester"));
		
		teilnehmenPage.neuerTeilnehmer("Peter");
		teilnehmenPage.neuerTeilnehmer("Peter");
		assertTrue(teilnehmenPage.zeigtTooltipMitFehlertext("Teilnehmer existiert bereits (NA)"));

		teilnehmenPage.neuerTeilnehmerMitAbsage("Peter");
		assertTrue(teilnehmenPage.zeigtTooltipMitFehlertext("Teilnehmer existiert bereits (NA)"));
	}
	
	private String erstelleTerminfindung(String veranstaltungName, String organisator)
	{
		erstellenPage.imBrowserGeoeffnet();
		return erstellenPage.terminfindungErstelltMitZeiten(veranstaltungName, organisator,
				Arrays.asList(LocalDate.now()), Arrays.asList(LocalTime.of(9, 0)), Arrays.asList(LocalTime.of(10, 0)));
	}
}
