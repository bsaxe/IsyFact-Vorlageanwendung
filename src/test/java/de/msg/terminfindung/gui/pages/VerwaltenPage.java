package de.msg.terminfindung.gui.pages;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.msg.terminfindung.gui.pages.fragments.FehlerTooltipFragment;
import de.msg.terminfindung.gui.pages.fragments.TerminfindungDetailsFragment;

public class VerwaltenPage {

	@Drone
	protected WebDriver browser;
	
	@ArquillianResource
	private URL baseUrl;
	
	private static String pageUrl = "verwaltenFlow?tfref=";

	@FindBy(xpath = ".//input[@value = 'Stammdaten bearbeiten']")
	private WebElement stammdatenBearbeitenButton;

	@FindBy(xpath = ".//input[@value = 'Termine löschen']")
	private WebElement termineLoeschenButton;

	@FindBy(xpath = ".//input[@value = 'Terminfindung abschliessen']")
	private WebElement terminfindungAbschliessenButton;

	@FindBy(xpath = ".//input[@value = 'Zur Teilnehmersicht']")
	private WebElement teilnehmerSichtButton;

	@FindBy(xpath = ".//input[@value = 'Zurück']")
	private WebElement stammdatenZurueckButton;

	@FindBy(xpath = ".//input[@value = 'Speichern']")
	private WebElement stammdatenSpeichernButton;

	@FindBy(xpath = ".//input[contains(@id, 'formular1')]")
	private WebElement stammdatenVeranstaltungNameInput;

	@FindBy(xpath = ".//input[contains(@id, 'formular3')]")
	private WebElement stammdatenVeranstaltungOrganisatorInput;

	@FindBy(xpath = ".//input[@value = 'Festlegen']")
	private WebElement abschliessenFestlegenButton;

	@FindBy(xpath = ".//input[@value = 'Zurück']")
	private WebElement abschliessenZurueckButton;

	@FindBy(xpath = ".//input[contains(@name, 'waehleEinenZeitraum')]")
	private List<WebElement> waehleZeitraumRadioSelectElements;

	@FindBy(xpath = ".//input[contains(@name, 'selectedZeitraum')]")
	private List<WebElement> waehleZeitraumCheckboxElements;

	@FindBy(xpath = ".//input[@value = 'Löschen']")
	private WebElement termineLoeschenLoeschenButton;

	@FindBy(xpath = ".//div[span[normalize-space(text())='Terminfindung Details']]/../..")
	private TerminfindungDetailsFragment terminfindungDetails;	

	@FindBy(xpath = ".//table[@class = 'selectAbschliessen']")
	private WebElement teilnahmeTabelleElement;

	@FindBy(xpath = ".//p[1]")
	private WebElement abgeschlossenNachrichtElement;
	
	@FindBy(xpath = ".//a[@data-toggle = 'tooltip']")
	private FehlerTooltipFragment fehlerTooltip;

	public void ladeBestehendeTerminfindungImBrowser(String terminfindungsRef) {
		browser.get(baseUrl + pageUrl + terminfindungsRef);
		Graphene.waitGui().until().element(teilnehmerSichtButton).is().visible();
	}

	public void aendereStammdaten(String neuerName, String neuerOrganisator) {
		stammdatenBearbeitenButton.click();
		Graphene.waitGui().until().element(stammdatenSpeichernButton).is().visible();

		stammdatenVeranstaltungNameInput.clear();
		stammdatenVeranstaltungNameInput.sendKeys(neuerName);

		stammdatenVeranstaltungOrganisatorInput.clear();
		stammdatenVeranstaltungOrganisatorInput.sendKeys(neuerOrganisator);

		stammdatenSpeichernButton.click();
	}

	public void schliesseTerminfindungAb(int festgelegterTerminIdx) {
		terminfindungAbschliessenButton.click();
		Graphene.waitGui().until().element(abschliessenFestlegenButton).is().visible();

		assertTrue(festgelegterTerminIdx < waehleZeitraumRadioSelectElements.size());

		waehleZeitraumRadioSelectElements.get(festgelegterTerminIdx).click();

		abschliessenFestlegenButton.click();
	}

	public void loescheDatum(int... zeitraumIdx) {
		termineLoeschenButton.click();
		Graphene.waitGui().until().element(termineLoeschenLoeschenButton).is().visible();

		for (int idx : zeitraumIdx) {
			waehleZeitraumCheckboxElements.get(idx).click();
		}

		termineLoeschenLoeschenButton.click();
		Graphene.waitGui().until().element(termineLoeschenLoeschenButton).is().visible();
	}

	public boolean stammdatenDerTerminfindungSind(String name, String organisator) {
	    return name.equals(terminfindungDetails.getVeranstaltungsName()) &&
		   organisator.equals(terminfindungDetails.getOrganisator());
	}

	public boolean stammdatenBearbeitenButtonIstAktiviert() {
	    return stammdatenBearbeitenButton.isEnabled();
	}

	public boolean terminfindungAbschliessenButtonIstAktiviert() {
	    return terminfindungAbschliessenButton.isEnabled();
	}

	public boolean termineLoeschenButtonIstAktiviert() {
	    return termineLoeschenButton.isEnabled();
	}

	public boolean teilnehmerSichtButtonIstAktiviert() {
	    return teilnehmerSichtButton.isEnabled();
	}

	public boolean enthaeltTermin(LocalDate tag, String zeitraum) {
	    String html = teilnahmeTabelleElement.getAttribute("outerHTML");
	    TeilnahmeTabelle tabelle = TeilnahmeTabelle.parseFromHtml(html);

	    return tabelle.enthaeltTagMitZeitraum(tag, zeitraum);
	}

	public boolean zeigtNachricht(String nachricht) {
	    return nachricht.equals(abgeschlossenNachrichtElement.getText());
	}

	public boolean zeigtTooltipMitFehlertext(String text) {
	    return text.equals(fehlerTooltip.getTooltipText());
	}
}
