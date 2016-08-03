package de.msg.terminfindung.gui.pages.fragments;

import org.jboss.arquillian.graphene.fragment.Root;
import org.openqa.selenium.WebElement;

public class FehlerTooltipFragment {

	@Root
	private WebElement root;

	public String getTooltipText()
	{
		return root.getAttribute("data-content");
	}
}
