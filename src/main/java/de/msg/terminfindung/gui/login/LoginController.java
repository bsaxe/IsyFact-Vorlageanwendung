package de.msg.terminfindung.gui.login;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Controller;

import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.logging.LogKategorie;
import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextImpl;
import de.bund.bva.pliscommon.sicherheit.Berechtigungsmanager;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungTechnicalException;
import de.msg.terminfindung.common.konstanten.EreignissSchluessel;
import de.msg.terminfindung.gui.terminfindung.AbstractController;
import de.msg.terminfindung.sicherheit.SerializableAufrufKontextImpl;

/**
 * Controller des Login Flows
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
@Controller
public class LoginController extends AbstractController<LoginModel> {

	private static final IsyLogger LOG = IsyLoggerFactory.getLogger(LoginController.class);
	
	Sicherheit<AufrufKontextImpl> sicherheit;
	AufrufKontextVerwalter<SerializableAufrufKontextImpl> aufrufKontextVerwalter;
	
	Sicherheit<AufrufKontextImpl> s;
	/**
	 * Initialisiert das Modell des Loeschen Flows
	 *
	 * @param model Das Modell
	 */
	public void initialisiereModel(LoginModel model) {
	}

	/**
	 * Führt den Login-Vorgang aus.
	 * 
	 * @param model	Das Modell
	 * @param context Der Login-Kontext
	 */
	public boolean performLogin(LoginModel model, MessageContext context) {

		LOG.infoFachdaten(LogKategorie.JOURNAL, EreignissSchluessel.MSG_LOGIN_STARTED, "Führe Login aus für Benutzer {}", model.getUsername());
		
		SerializableAufrufKontextImpl akontext= new SerializableAufrufKontextImpl();
		
		akontext.setDurchfuehrenderBenutzerKennung(model.getUsername());
		akontext.setDurchfuehrenderBenutzerPasswort(model.getPassword());
		
		aufrufKontextVerwalter.setAufrufKontext(akontext);

		try {
			@SuppressWarnings("unused")
			Berechtigungsmanager bmanager  = sicherheit.getBerechtigungsManagerUndAuthentifiziere(akontext);

			LOG.info(LogKategorie.JOURNAL,EreignissSchluessel.MSG_LOGIN_SUCCESS,"Authentifizierung war erfolgreich");
			
		}
		catch (AuthentifizierungTechnicalException e) {
			
			LOG.info(LogKategorie.JOURNAL, EreignissSchluessel.MSG_LOGIN_FAILED, "Authentifizierung ist fehlgeschlagen", e);
			
			context.addMessage(new MessageBuilder().error().defaultText("Authentifizierung ist fehlgeschlagen").build());
		    return false;
		}
		return true;
	}
	
	public Sicherheit<AufrufKontextImpl> getSicherheit() {
		return sicherheit;
	}

	public void setSicherheit(Sicherheit<AufrufKontextImpl> sicherheit) {
		this.sicherheit = sicherheit;
	}

	public AufrufKontextVerwalter<SerializableAufrufKontextImpl> getAufrufKontextVerwalter() {
		return aufrufKontextVerwalter;
	}

	public void setAufrufKontextVerwalter(
			AufrufKontextVerwalter<SerializableAufrufKontextImpl> aufrufKontextVerwalter) {
		this.aufrufKontextVerwalter = aufrufKontextVerwalter;
	}	
}
