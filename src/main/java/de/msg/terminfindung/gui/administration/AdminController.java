package de.msg.terminfindung.gui.administration;

import org.springframework.stereotype.Controller;

import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.msg.terminfindung.gui.terminfindung.AbstractController;

/**
 *  Controller des StartFlows
 *
 * @author msg systems ag, Maximilian Falter
 */
@Controller
public class AdminController extends AbstractController<AdminModel> {

	private static final IsyLogger LOG = IsyLoggerFactory.getLogger(AdminController.class);
	
	/**
	 * Führt den Login-Vorgang aus.
	 * 
	 * @param model Das Modell
	 */
	public void initialisiereModel(AdminModel model) {
	}

}
