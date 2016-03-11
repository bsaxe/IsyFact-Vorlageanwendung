package de.msg.terminfindung.gui.login;

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


import org.apache.log4j.Logger;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Controller;

import de.bund.bva.pliscommon.aufrufkontext.AufrufKontextVerwalter;
import de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextImpl;
import de.bund.bva.pliscommon.sicherheit.Berechtigungsmanager;
import de.bund.bva.pliscommon.sicherheit.Sicherheit;
import de.bund.bva.pliscommon.sicherheit.common.exception.AuthentifizierungTechnicalException;
import de.msg.terminfindung.gui.terminfindung.AbstractController;
import de.msg.terminfindung.sicherheit.SerializableAufrufKontextImpl;

/**
 * Controller des Login Flows
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
@Controller
public class LoginController extends AbstractController<LoginModel> {

	private static final Logger LOG = Logger.getLogger(LoginController.class);
	
	Sicherheit<AufrufKontextImpl> sicherheit;
	AufrufKontextVerwalter<SerializableAufrufKontextImpl> aufrufKontextVerwalter;
	
	Sicherheit<AufrufKontextImpl> s;
	/**
	 * Initialisiert das Modell des Loeschen Flows
	 *
	 * @param model Das Modell
	 */
	public void initialisiereModel(LoginModel model) {

		LOG.info("Initialisiere Modell");
	}

	/**
	 * Führt den Login-Vorgang aus.
	 * 
	 * @param model	Das Modell
	 * @param context Der Login-Kontext
	 */
	public boolean performLogin(LoginModel model, MessageContext context) {

		LOG.info("Führe Login aus für Benutzer " + model.getUsername());
		
		SerializableAufrufKontextImpl akontext= new SerializableAufrufKontextImpl();
		
		akontext.setDurchfuehrenderBenutzerKennung(model.getUsername());
		akontext.setDurchfuehrenderBenutzerPasswort(model.getPassword());
		
		aufrufKontextVerwalter.setAufrufKontext(akontext);

		try {
			@SuppressWarnings("unused")
			Berechtigungsmanager bmanager  = sicherheit.getBerechtigungsManagerUndAuthentifiziere(akontext);

			LOG.info("Authentifizierung war erfolgreich");
			
		}
		catch (AuthentifizierungTechnicalException e) {
			
			LOG.info("Authentifizierung ist fehlgeschlagen");
			
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
