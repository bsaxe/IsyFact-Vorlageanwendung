package de.msg.terminfindung.gui.terminfindung.verwalten.aktualisieren;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.exception.TerminfindungTechnicalException;
import de.msg.terminfindung.gui.terminfindung.AbstractController;

/**
 * Controller für den Aktualisieren flow
 * @author vadim
 *
 */
public class AktualisierenController extends AbstractController<AktualisierenModel>{

	private static final Logger LOG = Logger.getLogger(AktualisierenController.class);

	/**
	 * Initialisiert das Model mit einer vorgegebenen Terminfindung.
	 * 
	 * @param model Das Model
	 * @throws TerminfindungBusinessException TerminfindungTechnicalException
	 */
	public void initialisiereModel(AktualisierenModel model) throws TerminfindungTechnicalException, TerminfindungBusinessException {
		LOG.info("Initialisiere das Modell.");
		super.holeTerminfindung(model);
		model.setOrganisatorName(model.getTerminfindung().getOrganisator().getName());
		model.setVeranstaltungsName(model.getTerminfindung().getVeranstaltungName());
	}
	
	public boolean aktualisiereTerminfindung(AktualisierenModel model){
		
		List<ValidationMessage> validationMessages = new ArrayList<>();
		
		if (model.getVeranstaltungsName().isEmpty()) {
            validationMessages.add(new ValidationMessage("DA",
                    "formular1", "Name der Veranstaltung",
                    "Name der Veranstaltung kann nicht leer sein."));
        }
		if (model.getOrganisatorName().isEmpty()) {
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
				super.getAwk().aktualisiereTerminfindung(model.getTerminfindung(), model.getVeranstaltungsName(), 
						model.getOrganisatorName());
			} catch (TerminfindungBusinessException e) {
				LOG.error("Aktualisieren der Terminfindung fehlgeschlagen", e);				
			}
		}
		return true;
	}
}
