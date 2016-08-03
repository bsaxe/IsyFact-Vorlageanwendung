package de.msg.terminfindung.gui.pages;

import static org.junit.Assert.assertTrue;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("loginFlow")
public class LoginPage {
	
	@Drone
	private WebDriver browser;
	
	@FindBy(xpath = ".//input[contains(@id, 'username')]")
	private WebElement usernameElement;

	@FindBy(xpath = ".//input[contains(@id, 'password')]")
	private WebElement passwordElement;

	@FindBy(xpath = ".//input[contains(@value, 'Anmelden')]")
	private WebElement submitElement;

	@FindBy(xpath = ".//div[starts-with(@class, 'alert')]//strong")
	private WebElement anmeldungFehlgeschlagenNachrichtElement;

	@FindBy(xpath = ".//div[starts-with(@id, 'inhaltsbereichForm')]//p")
	private WebElement anmeldungErfolgreichNachrichtElement;

	public void loginMit(String username, String password) {
		Graphene.waitGui().until().element(passwordElement).is().visible();
		usernameElement.sendKeys(username);
		passwordElement.sendKeys(password);
		submitElement.click();
	}

	public void imBrowserGeoeffnet() {
		Graphene.goTo(LoginPage.class);
		Graphene.waitGui().until().element(passwordElement).is().visible();
	}

	public void zeigtLoginFehlgeschlagenNachricht(String message) {
		assertTrue(anmeldungFehlgeschlagenNachrichtElement.getText().contains(message));
	}

	public void zeigtLoginErfolgreichNachricht(String message) {
		assertTrue(anmeldungErfolgreichNachrichtElement.getText().contains(message));
	}
}
