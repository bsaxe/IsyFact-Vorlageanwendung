package de.msg.terminfindung.gui.pages.fragments;

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

import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TerminfindungDetailsFragment {

	@Root
	private WebElement root;
	
	@FindBy(xpath = ".//dt[text()='Veranstaltungsname']/following-sibling::dd[1]")
	private WebElement veranstaltungsNameElement;
	
	@FindBy(xpath = ".//dt[text()='Veranstaltungsname']/following-sibling::dd[2]")
	private WebElement veranstaltungOrganisatorElement;
	
	public String getVeranstaltungsName()
	{
		return veranstaltungsNameElement.getText();
	}
	
	public String getOrganisator()
	{
		return veranstaltungOrganisatorElement.getText();
	}
	
}
