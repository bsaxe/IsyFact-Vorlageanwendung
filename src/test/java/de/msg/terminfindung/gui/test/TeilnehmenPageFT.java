package de.msg.terminfindung.gui.test;

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
		
		teilnehmenPage.zeigtNameUndOrganisator("Test zeigt Daten an", "Tester");
	}

	@Test
	public void keineTeilnahmeMehrAnAbgeschlossenerTerminfindungMoeglich() {
		String ref = erstelleTerminfindung("Test abgeschlossene Terminfindung", "Tester");
		
		verwaltenPage.ladeBestehendeTerminfindungImBrowser(ref);
		verwaltenPage.schliesseTerminfindungAb(0);
		
		teilnehmenPage.ladeBestehendeTerminfindungImBrowser(ref);
		teilnehmenPage.teilnahmeButtonsSindDeaktiviert();
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

		teilnehmenPage.zeigtTeilnehmerMitZusageFuerTagUndZeitraum("Klaus", tage.get(0), "09:15 - 11:30");
		teilnehmenPage.zeigtTeilnehmerMitZusageFuerTagUndZeitraum("Maria", tage.get(0), "09:15 - 11:30");
		teilnehmenPage.zeigtTeilnehmerMitZusageFuerTagUndZeitraum("Maria", tage.get(1), "11:45 - 13:00");
		teilnehmenPage.zeigtTeilnehmerKannNicht("Peter");
	}

	@Test
	public void teilnahmeOhneNameNichtMoeglich() {
		teilnehmenPage.ladeBestehendeTerminfindungImBrowser(erstelleTerminfindung("Test Teilnahme ohne Name", "Tester"));
		
		teilnehmenPage.neuerTeilnehmer("");
		teilnehmenPage.zeigtTooltipMitFehlertext("Bitte Namen eingeben (NA)");

		teilnehmenPage.neuerTeilnehmerMitAbsage("");
		teilnehmenPage.zeigtTooltipMitFehlertext("Bitte Namen eingeben (NA)");
	}
	
	@Test
	public void teilnahmeTeilnehmerExistiertBereits()
	{
		teilnehmenPage.ladeBestehendeTerminfindungImBrowser(erstelleTerminfindung("Test Teilnahme Teilnehmer existiert bereits", "Tester"));
		
		teilnehmenPage.neuerTeilnehmer("Peter");
		teilnehmenPage.neuerTeilnehmer("Peter");
		teilnehmenPage.zeigtTooltipMitFehlertext("Teilnehmer existiert bereits (NA)");

		teilnehmenPage.neuerTeilnehmerMitAbsage("Peter");
		teilnehmenPage.zeigtTooltipMitFehlertext("Teilnehmer existiert bereits (NA)");
	}
	
	private String erstelleTerminfindung(String veranstaltungName, String organisator)
	{
		erstellenPage.imBrowserGeoeffnet();
		return erstellenPage.terminfindungErstelltMitZeiten(veranstaltungName, organisator,
				Arrays.asList(LocalDate.now()), Arrays.asList(LocalTime.of(9, 0)), Arrays.asList(LocalTime.of(10, 0)));
	}
}
