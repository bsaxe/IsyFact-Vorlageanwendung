package de.msg.terminfindung.gui.test;

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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import de.msg.terminfindung.gui.pages.ErstellenPage;
import de.msg.terminfindung.gui.pages.VerwaltenPage;

@RunWith(Arquillian.class)
@RunAsClient
public class VerwaltenPageFT {

	@Page
	private VerwaltenPage verwaltenPage;

	@Page
	private ErstellenPage erstellenPage;

	@Test
	public void aendereStammdatenEinerTerminfindung() {
		verwaltenPage.ladeBestehendeTerminfindungImBrowser(erstelleTerminfindung("Test Stammdaten ändern", "Tester"));
		
		verwaltenPage.aendereStammdaten("neuer Name", "neuer Organisator");
		
		verwaltenPage.stammdatenDerTerminfindungSind("neuer Name", "neuer Organisator");
	}
	
	@Test
	public void stammdatenVeranstaltungsnameDarfNichtLeerSein() {
		verwaltenPage.ladeBestehendeTerminfindungImBrowser(erstelleTerminfindung("Test Stammdaten ändern Name leer", "Tester"));
		
		verwaltenPage.aendereStammdaten("", "neuer Organisator");
		
		verwaltenPage.zeigtTooltipMitFehlertext("Name der Veranstaltung kann nicht leer sein. (DA)");
	}
	
	@Test
	public void stammdatenOrganisatorDarfNichtLeerSein() {
		verwaltenPage.ladeBestehendeTerminfindungImBrowser(erstelleTerminfindung("Test Stammdaten ändern Organisator leer", "Tester"));
		
		verwaltenPage.aendereStammdaten("neuer Name", "");
		
		verwaltenPage.zeigtTooltipMitFehlertext("Name des Organisators kann nicht leer sein (DA)");
	}

	@Test
	public void abgeschlosseneTerminfindungKannNichtGeaendertWerden() {
		verwaltenPage.ladeBestehendeTerminfindungImBrowser(erstelleTerminfindung("Test abgeschlossene Terminfindung", "Tester"));
		
		verwaltenPage.schliesseTerminfindungAb(0);
		
		verwaltenPage.stammdatenBearbeitenButtonIstDeaktiviert();
		verwaltenPage.termineLoeschenButtonIstDeaktiviert();
		verwaltenPage.terminfindungAbschliessenButtonIstDeaktiviert();
		verwaltenPage.teilnehmerSichtButtonIstAktiviert();
	}

	@Test
	public void termineLoeschen() {
		erstellenPage.imBrowserGeoeffnet();
		String tfRef = erstellenPage.terminfindungErstelltMitZeiten("Test Termin löschen", "Tester",
				Arrays.asList(LocalDate.now(), LocalDate.now().plusDays(1)),
				Arrays.asList(LocalTime.of(9, 0), LocalTime.of(12, 0)),
				Arrays.asList(LocalTime.of(10, 0), LocalTime.of(13, 0)));

		verwaltenPage.ladeBestehendeTerminfindungImBrowser(tfRef);
		
		verwaltenPage.loescheDatum(0, 3);
		
		verwaltenPage.enthaeltNichtTermin(LocalDate.now(), "09:00 - 10:00");
		verwaltenPage.enthaeltNichtTermin(LocalDate.now().plusDays(1), "12:00 - 13:00");
	}

	@Test
	public void terminfindungAbschliessen() {
		erstellenPage.imBrowserGeoeffnet();
		String tfRef = erstellenPage.terminfindungErstelltMitZeiten("Test Terminfindung abschließen", "Test",
				Arrays.asList(LocalDate.now(), LocalDate.now().plusDays(1)), Arrays.asList(LocalTime.of(9, 0)),
				Arrays.asList(LocalTime.of(10, 0)));

		String terminTag = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

		verwaltenPage.ladeBestehendeTerminfindungImBrowser(tfRef);
		
		verwaltenPage.schliesseTerminfindungAb(1);
		
		verwaltenPage.zeigtNachricht(
				"Die Terminfindung für diese Veranstaltung ist abgeschlossen.\nDer ausgewählte Termin ist der "
						+ terminTag + " (Zeitraum: 09:00 - 10:00).");
		verwaltenPage.stammdatenBearbeitenButtonIstDeaktiviert();
		verwaltenPage.termineLoeschenButtonIstDeaktiviert();
		verwaltenPage.terminfindungAbschliessenButtonIstDeaktiviert();
		verwaltenPage.teilnehmerSichtButtonIstAktiviert();
	}
	
	private String erstelleTerminfindung(String veranstaltungName, String organisator)
	{
		erstellenPage.imBrowserGeoeffnet();
		return erstellenPage.terminfindungErstelltMitZeiten(veranstaltungName, organisator,
				Arrays.asList(LocalDate.now()), Arrays.asList(LocalTime.of(9, 0)), Arrays.asList(LocalTime.of(10, 0)));
	}
}
