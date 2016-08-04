package de.msg.terminfindung.gui.pages;

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

	public void stammdatenDerTerminfindungSind(String name, String organisator) {
		assertEquals(terminfindungDetails.getVeranstaltungsName(), name);
		assertEquals(terminfindungDetails.getOrganisator(), organisator);
	}

	public void stammdatenBearbeitenButtonIstAktiviert() {
		assertTrue(stammdatenBearbeitenButton.isEnabled());
	}

	public void stammdatenBearbeitenButtonIstDeaktiviert() {
		assertFalse(stammdatenBearbeitenButton.isEnabled());
	}

	public void terminfindungAbschliessenButtonIstAktiviert() {
		assertTrue(terminfindungAbschliessenButton.isEnabled());
	}

	public void terminfindungAbschliessenButtonIstDeaktiviert() {
		assertFalse(terminfindungAbschliessenButton.isEnabled());
	}

	public void termineLoeschenButtonIstAktiviert() {
		assertTrue(termineLoeschenButton.isEnabled());
	}

	public void termineLoeschenButtonIstDeaktiviert() {
		assertFalse(termineLoeschenButton.isEnabled());
	}

	public void teilnehmerSichtButtonIstAktiviert() {
		assertTrue(teilnehmerSichtButton.isEnabled());
	}

	public void teilnehmerSichtButtonIstDeaktiviert() {
		assertFalse(teilnehmerSichtButton.isEnabled());
	}

	public void enthaeltNichtTermin(LocalDate tag, String zeitraum) {
		String html = teilnahmeTabelleElement.getAttribute("outerHTML");
		TeilnahmeTabelle tabelle = TeilnahmeTabelle.parseFromHtml(html);

		assertFalse(tabelle.enthaeltTagMitZeitraum(tag, zeitraum));
	}

	public void zeigtNachricht(String nachricht) {
		String foo = abgeschlossenNachrichtElement.getText();
		assertTrue(foo.equals(nachricht));
	}

	public void zeigtTooltipMitFehlertext(String text) {
		assertEquals(text, fehlerTooltip.getTooltipText());
	}
}
