package de.msg.terminfindung.gui.test;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import de.msg.terminfindung.gui.pages.LoginPage;

@RunWith(Arquillian.class)
@RunAsClient
public class LoginPageFT {

	@Page
	private LoginPage loginPage;

	@Test
	public void fehlgeschlagenerLogin() {
		loginPage.imBrowserGeoeffnet();

		loginPage.loginMit("wronguser", "wrongpw");

		loginPage.zeigtLoginFehlgeschlagenNachricht("Authentifizierung ist fehlgeschlagen");
	}

	@Test
	public void erfolgreicherLogin() {
		loginPage.imBrowserGeoeffnet();

		loginPage.loginMit("admin", "admin");

		loginPage.zeigtLoginErfolgreichNachricht("Anmeldung erfolgreich!");
	}
}
