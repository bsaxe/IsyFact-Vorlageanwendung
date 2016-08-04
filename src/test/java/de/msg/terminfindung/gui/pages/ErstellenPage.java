package de.msg.terminfindung.gui.pages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import de.msg.terminfindung.gui.pages.fragments.FehlerTooltipFragment;

@Location("erstellenFlow")
public class ErstellenPage {

	@Drone
	protected WebDriver browser;

	@FindBy(xpath = ".//input[contains(@id, 'name')]")
	private WebElement veranstaltungNameInput;

	@FindBy(xpath = ".//input[contains(@id, 'orgName')]")
	private WebElement veranstaltungOrganisatorInput;

	@FindBy(xpath = ".//input[contains(@id, 'newDate')]")
	private WebElement neuesDatumInput;

	@FindBy(xpath = ".//input[@value = 'Tag hinzufügen']")
	private WebElement tagHinzuButton;

	@FindBy(xpath = ".//input[@value = 'Weiter']")
	private WebElement weiterButton;

	@FindBy(xpath = ".//table[@class = 'chosenTable']//td[1]")
	private List<WebElement> ausgewaehlteTageElements;

	@FindBy(xpath = ".//table[@class = 'chosenTable']//td[2]/a")
	private List<WebElement> ausgewaehlteTageLoeschenElements;

	@FindBy(xpath = ".//a[@data-toggle = 'tooltip']")
	private FehlerTooltipFragment fehlerTooltip;

	@FindBy(xpath = ".//div[@class='panel-body']/div/table/tbody/tr/td[1]")
	private List<WebElement> ausgewaehlteTageBeiZeitenElements;

	@FindBy(xpath = ".//table[@class = 'tableZeiten']//a[contains(@id, 'zeitraeume')]")
	private List<WebElement> zeitraumHinzufuegenElements;

	@FindBy(xpath = ".//select[contains(@id, 'vonZeit')]")
	private List<WebElement> zeitraumNeuVonElements;

	@FindBy(xpath = ".//select[contains(@id, 'bisZeit')]")
	private List<WebElement> zeitraumNeuBisElements;

	@FindBy(xpath = ".//input[@value = 'Terminfindung erstellen']")
	private WebElement terminfindungErstellenButton;

	@FindBy(xpath = ".//a[contains(@id, 'organisatorSichtLink')]")
	private WebElement verwaltenLink;

	@FindBy(xpath = ".//a[contains(@id, 'teilnehmerSichtLink')]")
	private WebElement teilnehmenLink;

	private static DateTimeFormatter ddMMyyyy = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static DateTimeFormatter HHmm = DateTimeFormatter.ofPattern("HH:mm");

	public void imBrowserGeoeffnet() {
		Graphene.goTo(ErstellenPage.class);
		Graphene.waitGui().until().element(weiterButton).is().visible();
	}

	public void terminfindungErstelltOhneZeiten(String name, String organisator, List<LocalDate> tage) {
		veranstaltungNameInput.clear();
		veranstaltungNameInput.sendKeys(name);
		veranstaltungOrganisatorInput.clear();
		veranstaltungOrganisatorInput.sendKeys(organisator);

		deleteAllDays();

		for (LocalDate tag : tage) {
			fuegeTagHinzu(tag);
		}

		Graphene.waitGui().until().element(weiterButton).is().visible();
		weiterButton.click();
	}

	public String terminfindungErstelltMitZeiten(String name, String organisator, List<LocalDate> tage,
			List<LocalTime> zeitenVon, List<LocalTime> zeitenBis) {
		terminfindungErstelltOhneZeiten(name, organisator, tage);
		fuegeZeitenHinzu(tage, zeitenVon, zeitenBis);

		Graphene.waitGui().until().element(terminfindungErstellenButton).is().visible();
		terminfindungErstellenButton.click();

		Graphene.waitGui().until().element(teilnehmenLink).is().visible();
		return extractUuidFromLink(teilnehmenLink.getAttribute("href"));
	}

	private void fuegeZeitenHinzu(List<LocalDate> tage, List<LocalTime> zeitenVon, List<LocalTime> zeitenBis) {
		for (LocalDate tag : tage) {
			for (int i = 0; i < zeitenVon.size(); i++) {
				fuegeZeitraumHinzu(tag, zeitenVon.get(i).format(HHmm), zeitenBis.get(i).format(HHmm));
			}
		}
	}

	private String extractUuidFromLink(String link) {
		Pattern urlWithUuidPattern = Pattern
				.compile("http://(.)+tfref=([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})+");
		Matcher m = urlWithUuidPattern.matcher(link);

		assertTrue(m.find());
		return m.group(2);
	}

	public void loescheTag(LocalDate tag) {
		int idxOfDayElement = getIdxOfDayFromListOfWebElements(tag, ausgewaehlteTageElements);
		ausgewaehlteTageLoeschenElements.get(idxOfDayElement).click();
	}

	public void loescheAlleTage() {
		while (ausgewaehlteTageLoeschenElements.size() > 0) {
			ausgewaehlteTageLoeschenElements.get(0).click();
		}
	}

	public void fuegeTagHinzu(LocalDate neuerTag) {
		neuesDatumInput.clear();
		neuesDatumInput.sendKeys(neuerTag.format(ddMMyyyy));
		tagHinzuButton.click();
	}

	public void fuegeNTageHinzu(int n) {
		while (n-- > 0) {
			fuegeTagHinzu(LocalDate.now().plusDays(n));
		}
	}

	public void fuegeZeitraumHinzu(LocalDate tag, String von, String bis) {
		int idxOfDay = getIdxOfDayFromListOfWebElements(tag, ausgewaehlteTageBeiZeitenElements);
		Select fromSelect = new Select(zeitraumNeuVonElements.get(idxOfDay));
		Select toSelect = new Select(zeitraumNeuBisElements.get(idxOfDay));

		fromSelect.selectByValue(von);
		toSelect.selectByValue(bis);

		zeitraumHinzufuegenElements.get(idxOfDay).click();
	}
	
	public void setzeNameUndOrganisator(String name, String organisator)
	{
		veranstaltungNameInput.clear();
		veranstaltungNameInput.sendKeys(name);
		
		veranstaltungOrganisatorInput.clear();
		veranstaltungOrganisatorInput.sendKeys(organisator);
	}

	public void erstelleTerminfindung() {
		weiterButton.click();
	}

	public void erstelleTerminfindungAusZeitraeumeAnsicht() {
		terminfindungErstellenButton.click();
	}

	private int getIdxOfDayFromListOfWebElements(LocalDate date, List<WebElement> elements) {
		for (int i = 0; i < elements.size(); i++) {
			WebElement we = elements.get(i);
			if (we.getText().equals(date.format(ddMMyyyy))) {
				return i;
			}
		}

		assertTrue(false);
		return -1;
	}

	public void tagIstAusgewaehlt(LocalDate tag) {
		assertTrue(selectedDaysContains(tag));
	}

	public void tagIstNichtAusgewaehlt(LocalDate tag) {
		assertFalse(selectedDaysContains(tag));
	}

	public void zeigtTooltipMitFehlertext(String text) {
		assertEquals(text, fehlerTooltip.getTooltipText());
	}

	public void tageZurZeitauswahlVorhanden(List<LocalDate> tage) {
		// List<LocalDate> shownDates = new ArrayList<>();
		// for (WebElement we : ausgewaehlteTageBeiZeitenElements) {
		// shownDates.add(LocalDate.parse(we.getText(), ddMMyyyy));
		// }
		//
		// for (LocalDate tag : tage) {
		// assertTrue(shownDates.contains(tag));
		// }
		assertTrue(ausgewaehlteTageBeiZeitenElements.stream().map(we -> LocalDate.parse(we.getText(), ddMMyyyy))
				.collect(Collectors.toList()).equals(tage));
	}

	public void enthaeltLinkZuTeilnehmerSicht() {
		String link = teilnehmenLink.getAttribute("href");
		assertNotEquals(-1, link.indexOf("teilnehmenFlow?tfref="));
	}

	public void enthaeltLinkZuOrganisatorSicht() {
		String link = verwaltenLink.getAttribute("href");
		assertNotEquals(-1, link.indexOf("verwaltenFlow?tfref="));
	}

	public void weiterButtonIstDeaktiviert() {
		assertFalse(weiterButton.isEnabled());
	}

	private void deleteAllDays() {
		while (!ausgewaehlteTageLoeschenElements.isEmpty()) {
			ausgewaehlteTageLoeschenElements.get(0).click();
		}
	}

	private boolean selectedDaysContains(LocalDate tag) {
		// List<LocalDate> selectedDays = new ArrayList<>();
		//
		// for (WebElement we : ausgewaehlteTageElements)
		// selectedDays.add(LocalDate.parse(we.getText(), ddMMyyyy));
		//
		// return selectedDays.contains(tag);

		return ausgewaehlteTageElements.stream().anyMatch(we -> LocalDate.parse(we.getText(), ddMMyyyy).equals(tag));
	}
}
