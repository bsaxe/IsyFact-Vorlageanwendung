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

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("adminFlow")
public class AdminPage {
    
    	@FindBy(xpath = ".//div[contains(@class, 'panel-group')]")
    	private WebElement panelGroup;

	@FindBy(xpath = ".//div[contains(@class, 'panel-heading')]/div/span")
	private List<WebElement> panelHeadingElements;

	public void alsAdminAngemeldetUndImBrowserGeoeffnet() {
		LoginPage loginPage = Graphene.goTo(LoginPage.class);
		loginPage.loginMit("admin", "admin");
		Graphene.goTo(AdminPage.class);
		Graphene.waitModel().until().element(panelGroup).is().visible();
	}

	public boolean zeigtAlleTerminfindungen() {
	    return panelHeadingElements.stream().anyMatch(we -> we.getText().equals("Alle Terminfindungen"));
	}

}
