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

public class TeilnehmenPage {

	@Drone
	protected WebDriver browser;
	
	@ArquillianResource
	private URL baseUrl;
	
	private static String pageUrl = "teilnehmenFlow?tfref=";

	@FindBy(xpath = ".//input[contains(@value, 'Speichern')]")
	private WebElement speichernButton;

	@FindBy(xpath = ".//input[contains(@value, 'Ich kann nicht')]")
	private WebElement absagenButton;

	@FindBy(xpath = ".//div[span[normalize-space(text()) = 'Terminfindung Details']]/../..")
	private TerminfindungDetailsFragment terminfindungDetails;
	
	@FindBy(xpath = ".//a[@data-toggle = 'tooltip']")
	private FehlerTooltipFragment fehlerTooltip;

	@FindBy(xpath = ".//input[contains(@id, 'teilnehmerName')]")
	private WebElement teilnehmerNameInput;

	@FindBy(xpath = ".//table//input[@type = 'radio']")
	private List<WebElement> zusagenRadioSelectElements;

	@FindBy(xpath = ".//table[@class = 'selectAbschliessen']")
	private WebElement teilnahmeTabelleElement;

	public void ladeBestehendeTerminfindungImBrowser(String terminfindungsRef) {
		browser.get(baseUrl + pageUrl + terminfindungsRef);
		Graphene.waitGui().until().element(speichernButton).is().visible();
	}

	public void neuerTerminwunsch(int... terminWunschIndex) {
		for (int idx : terminWunschIndex) {
			assertTrue(idx < zusagenRadioSelectElements.size());
			zusagenRadioSelectElements.get(idx).click();
		}
	}

	public void neuerTeilnehmer(String name) {
		teilnehmerNameInput.clear();
		teilnehmerNameInput.sendKeys(name);

		speichernButton.click();
		Graphene.waitGui().until().element(speichernButton).is().visible();
	}
	
	public void neuerTeilnehmerMitAbsage(String name)
	{
		teilnehmerNameInput.clear();
		teilnehmerNameInput.sendKeys(name);
		
		absagenButton.click();
		Graphene.waitGui().until().element(speichernButton).is().visible();
	}

	public void zeigtNameUndOrganisator(String name, String organisator) {
		assertEquals(terminfindungDetails.getVeranstaltungsName(), name);
		assertEquals(terminfindungDetails.getOrganisator(), organisator);
	}

	public void zeigtTeilnehmerMitZusageFuerTagUndZeitraum(String name, LocalDate tag, String zeitraum) {
		String html = teilnahmeTabelleElement.getAttribute("outerHTML");
		TeilnahmeTabelle tabelle = TeilnahmeTabelle.parseFromHtml(html);

		assertTrue(tabelle.hatZugesagt(name, tag, zeitraum) || tabelle.hatBedingtZugesagt(name, tag, zeitraum));
	}

	public void teilnahmeButtonsSindDeaktiviert() {
		assertFalse(speichernButton.isEnabled());
		assertFalse(absagenButton.isEnabled());
	}
	
	public void zeigtTooltipMitFehlertext(String text) {
		assertEquals(text, fehlerTooltip.getTooltipText());
	}

	public void zeigtTeilnehmerKannNicht(String name) {
		String html = teilnahmeTabelleElement.getAttribute("outerHTML");
		TeilnahmeTabelle tabelle = TeilnahmeTabelle.parseFromHtml(html);
		
		assertTrue(tabelle.hatAllesAbgesagt(name));
	}

}
