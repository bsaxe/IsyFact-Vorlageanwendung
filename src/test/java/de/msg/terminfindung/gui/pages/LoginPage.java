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
