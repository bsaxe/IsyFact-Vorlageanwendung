package de.msg.terminfindung.gui.terminfindung.verwalten.aktualisieren;

import java.util.ArrayList;
import java.util.List;

import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.exception.TerminfindungTechnicalException;
import de.msg.terminfindung.gui.terminfindung.AbstractController;

/**
 * Controller für den Aktualisieren flow
 * @author vadim
 *
 */
public class AktualisierenController extends AbstractController<AktualisierenModel>{

	private static final IsyLogger LOG = IsyLoggerFactory.getLogger(AktualisierenController.class);

	/**
	 * Initialisiert das Model mit einer vorgegebenen Terminfindung.
	 * 
	 * @param model Das Model
	 * @throws TerminfindungBusinessException TerminfindungTechnicalException
	 */
	public void initialisiereModel(AktualisierenModel model) throws TerminfindungTechnicalException, TerminfindungBusinessException {
		LOG.debug("Initialisiere das AktualisierenModell.");
		super.holeTerminfindung(model);
	}
	
	public boolean aktualisiereTerminfindung(AktualisierenModel model){
		
		List<ValidationMessage> validationMessages = new ArrayList<>();
		
		if (model.getTerminfindung().getVeranstaltungName().isEmpty()) {
            validationMessages.add(new ValidationMessage("DA",
                    "formular1", "Name der Veranstaltung",
                    "Name der Veranstaltung kann nicht leer sein."));
        }
		if (model.getTerminfindung().getOrganisator().getName().isEmpty()) {
            validationMessages.add(new ValidationMessage("DA",
                    "formular3", "Name des Organisators",
                    "Name des Organisators kann nicht leer sein"));
        }
		
		if(!validationMessages.isEmpty()){
			this.globalFlowController.getValidationController().processValidationMessages(validationMessages);
			return false;
		}
		else{
			try {
				super.getAwk().aktualisiereTerminfindung(model.getTerminfindung(), model.getTerminfindung().getVeranstaltungName(), 
						model.getTerminfindung().getOrganisator().getName());
			} catch (TerminfindungBusinessException e) {
				LOG.error("Aktualisieren der Terminfindung fehlgeschlagen", e);				
			}
		}
		return true;
	}
}
