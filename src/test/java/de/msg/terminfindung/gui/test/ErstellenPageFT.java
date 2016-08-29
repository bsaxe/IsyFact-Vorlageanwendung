package de.msg.terminfindung.gui.test;

import static org.junit.Assert.assertFalse;

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

import static org.junit.Assert.assertTrue;

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
import java.util.List;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.msg.terminfindung.gui.pages.ErstellenPage;
import de.msg.terminfindung.gui.pages.TeilnehmenPage;


@RunWith(Arquillian.class)
@RunAsClient
public class ErstellenPageFT {

    @Page
    private ErstellenPage erstellenPage;

    @Page
    private TeilnehmenPage teilnehmenPage;

    @Test
    public void fuegeNeuenTagHinzu() {
	LocalDate heuteIn5Tagen = LocalDate.now().plusDays(5);

	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.fuegeTagHinzu(heuteIn5Tagen);

	assertTrue(erstellenPage.tagIstAusgewaehlt(heuteIn5Tagen));
    }

    @Test
    public void fuegeExistierendenTagHinzu() {
	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.fuegeTagHinzu(LocalDate.now());

	assertTrue(erstellenPage.zeigtTooltipMitFehlertext("Datum bereits hinzugefügt (DA)"));
    }

    @Test
    public void fuegeTagInDerVergangenheitHinzu() {
	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.fuegeTagHinzu(LocalDate.now().minusDays(1));

	assertTrue(erstellenPage.zeigtTooltipMitFehlertext("Das Datum darf nicht in der Vergangenheit liegen (DA)"));
    }

    @Test
    public void ueberschreiteMaxZulaessigeAnzahlVonTagen() {
	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.loescheAlleTage();
	erstellenPage.fuegeNTageHinzu(6);

	assertTrue(erstellenPage.zeigtTooltipMitFehlertext("Bereits max. Anzahl an Daten hinzugefügt (DA)"));
    }

    @Test
    public void loescheTag() {
	LocalDate date = LocalDate.now().plusDays(5);

	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.fuegeTagHinzu(date);
	erstellenPage.loescheTag(date);

	assertTrue(erstellenPage.tagIstNichtAusgewaehlt(date));
    }

    @Test
    public void terminfindungOhneDatumErstellenNichtMoeglich() {
	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.loescheAlleTage();

	assertFalse(erstellenPage.weiterButtonIstAktiviert());
    }

    @Test
    public void terminfindungOhneNameUndOrganisatorNichtMoeglich() {
	erstellenPage.imBrowserGeoeffnet();

	erstellenPage.setzeNameUndOrganisator("", "Tester");
	erstellenPage.erstelleTerminfindung();
	assertTrue(erstellenPage.zeigtTooltipMitFehlertext("Benötigtes Feld (TI)"));

	erstellenPage.setzeNameUndOrganisator("Test Veranstaltung", "");
	erstellenPage.erstelleTerminfindung();
	assertTrue(erstellenPage.zeigtTooltipMitFehlertext("Benötigtes Feld (NA)"));
    }

    @Test
    public void zeitraumExistiertBereits() {
	LocalDate heute = LocalDate.now();

	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.terminfindungErstelltOhneZeiten("Test Zeitraum schon vorhanden", "Tester", Arrays.asList(heute));
	erstellenPage.fuegeZeitraumHinzu(heute, "09:00", "10:00");
	erstellenPage.fuegeZeitraumHinzu(heute, "09:00", "10:00");

	assertTrue(erstellenPage.zeigtTooltipMitFehlertext("Zeitraum existiert bereits. (DA)"));
    }

    @Test
    public void zeitraumBeginntNachEnde() {
	LocalDate heute = LocalDate.now();

	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.terminfindungErstelltOhneZeiten("Test Zeitraum beginnt nach Ende", "Tester",
		Arrays.asList(heute));
	erstellenPage.fuegeZeitraumHinzu(heute, "10:00", "09:00");

	assertTrue(erstellenPage.zeigtTooltipMitFehlertext("Zeitraum startet nach seinem Ende. (DA)"));
    }

    @Test
    public void zeitraumMitGleichenStartUndEndeZeiten() {
	LocalDate heute = LocalDate.now();

	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.terminfindungErstelltOhneZeiten("Test Zeitraum mit gleicher Start- und Endzeit", "Tester",
		Arrays.asList(heute));
	erstellenPage.fuegeZeitraumHinzu(heute, "10:00", "10:00");

	assertTrue(erstellenPage.zeigtTooltipMitFehlertext("Zeitraum beginnt und Enden um die gleiche Uhrzeit. (DA)"));
    }

    @Test
    public void erstelleTerminfindungMitDatumOhneZeitraumzuordnung() {
	LocalDate heute = LocalDate.now();
	String heute_ddMMyy = heute.format(DateTimeFormatter.ofPattern("dd.MM.yy"));

	erstellenPage.terminfindungErstelltOhneZeiten("Kein Zeitraum", "zugeordnet", Arrays.asList(heute));
	erstellenPage.erstelleTerminfindungAusZeitraeumeAnsicht();

	assertTrue(erstellenPage.zeigtTooltipMitFehlertext("Dem Datum " + heute_ddMMyy + " ist kein Zeitraum zugeordnet. (DA)"));
    }

    @Test
    public void nachErstellungVonTerminfindungSindTageFuerZeitenVorhanden() {
	List<LocalDate> tage = Arrays.asList(LocalDate.now().plusDays(10), LocalDate.now().plusDays(11));

	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.terminfindungErstelltOhneZeiten("Kindergeburstag", "Klaus", tage);

	assertTrue(erstellenPage.tageZurZeitauswahlVorhanden(tage));
    }

    @Test
    public void nachErstellungVonTerminfindungLinksAufTeilnehmerUndOrganisatorSicht() {
	erstellenPage.imBrowserGeoeffnet();
	erstellenPage.terminfindungErstelltMitZeiten("Kindergeburtstag", "Klaus",
		Arrays.asList(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2)),
		Arrays.asList(LocalTime.of(9, 15), LocalTime.of(10, 45)),
		Arrays.asList(LocalTime.of(11, 30), LocalTime.of(13, 0)));

	assertTrue(erstellenPage.enthaeltLinkZuTeilnehmerSicht());
	assertTrue(erstellenPage.enthaeltLinkZuOrganisatorSicht());
    }
}