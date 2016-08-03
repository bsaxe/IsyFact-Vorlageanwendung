package de.msg.terminfindung.gui.pages.fragments;

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
