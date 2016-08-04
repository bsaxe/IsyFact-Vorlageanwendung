package de.msg.terminfindung.gui.pages;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("adminFlow")
public class AdminPage {

	@FindBy(xpath = ".//div[contains(@class, 'panel-heading')]/div/span")
	private List<WebElement> panelHeadingElements;

	public void alsAdminAngemeldetUndImBrowserGeoeffnet() {
		LoginPage loginPage = Graphene.goTo(LoginPage.class);
		loginPage.loginMit("admin", "admin");
		Graphene.goTo(AdminPage.class);
		Graphene.waitGui().until().element(panelHeadingElements.get(0)).is().visible();
	}

	public void zeigtAlleTerminfindungen() {
		assertTrue(panelHeadingElements.stream().anyMatch(we -> we.getText().equals("Alle Terminfindungen")));
	}

}
