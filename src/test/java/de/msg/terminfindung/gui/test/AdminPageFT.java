package de.msg.terminfindung.gui.test;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import de.msg.terminfindung.gui.pages.AdminPage;

@RunWith(Arquillian.class)
@RunAsClient
public class AdminPageFT {

	@Page
	private AdminPage adminPage;
	
	@Test
	public void zeigtBeimStartAlleTerminfindungen() {
		adminPage.alsAdminAngemeldetUndImBrowserGeoeffnet();
		
		adminPage.zeigtAlleTerminfindungen();
	}
}
