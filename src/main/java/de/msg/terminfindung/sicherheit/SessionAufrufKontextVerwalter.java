package de.msg.terminfindung.sicherheit;

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


import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextVerwalterImpl;
import de.msg.terminfindung.gui.login.LoginController;

/**
 * @author jaegerd
 *
 * Implementiert einen Aufrufkontext-Verwalter, der den Aufrufkontext zusätzlich in der Session speichert.
 * Der Aufrufkontext-Verwalter nutzt im Hintergrund ein Objekt der Klasse @link{LoginVerwalter}
 * Der @link{LoginVerwalter} muss in der Spring-Konfiguration im Session Scope liegen.
 * Die Implementierung funktioniert nur für Single-Server-Anwendungen bzw. für Anwendungen 
 * in einem Cluster, bei denen ein Load-Balancer einen Benutzer immer auf den gleichen Server schickt
 * (Sticky Sessions).
 *
 */
public class SessionAufrufKontextVerwalter<T extends SerializableAufrufKontextImpl> extends AufrufKontextVerwalterImpl<T>{

	private static final IsyLogger LOG = IsyLoggerFactory.getLogger(LoginController.class);

	/**
	 * Der LoginVerwalter, der den Aufrufkontext in der Session zwischenspeichert.
	 * Der AufrufKontextVerwalter selbst ist Thread-Scoped, es kann darum nicht 
	 * garantiert werden, dass bei einem Request ein AufrufKontext gespeichert ist.
	 */
	LoginVerwalter<T> loginVerwalter;
	
	/* (non-Javadoc)
	 * @see de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextVerwalterImpl#getAufrufKontext()
	 */
	@Override
	public T getAufrufKontext() {
		
		// Überprüfe, ob einen Aufrufkontext gespeichert ist.
		if (super.getAufrufKontext() == null) {
			// Wenn keine Aufrufkontext gespeichert ist, lese ihn aus dem LoginVerwalter.
			LOG.debug("Lese Aufrufkontext aus LoginVerwalter");
			T akontext = loginVerwalter.getAufrufKontext();

			// Ein paar Debug-Ausgaben
			if (akontext != null) {
				LOG.debugFachdaten("Aufrufkontext erfolgreich ermittelt für Benutzer: {}", akontext.getDurchfuehrenderBenutzerKennung());
				
			} else {
				LOG.debug("Kein Aufrufkontext im LoginVerwalter vorhangen (null value)");
			}
			super.setAufrufKontext(akontext);
		}		
		return super.getAufrufKontext();
	}
	
	/* (non-Javadoc)
	 * @see de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextVerwalterImpl#setAufrufKontext(de.bund.bva.pliscommon.aufrufkontext.impl.AufrufKontextImpl)
	 */
	@Override
	public void setAufrufKontext(T akontext) {
		
		// Jedesmal, wenn der Aufrufkontext neu gesetzt wird, setzt ihn auch in der Session.
		loginVerwalter.setAufrufKontext(akontext);

		// Ein paar Debug-Ausgaben
		if (akontext != null) {
			LOG.debugFachdaten("Speichere Aufrufkontext in LoginVerwalter für Benutzer: {}",  akontext.getDurchfuehrenderBenutzerKennung());
			
		} else {
			LOG.debug("Speichere Aufrufkontext {} in LoginVerwalter", akontext);
		}
		super.setAufrufKontext(akontext);
	}
	
	public LoginVerwalter<T> getLoginVerwalter() {
		return loginVerwalter;
	}

	public void setLoginVerwalter(LoginVerwalter<T> loginVerwalter) {
		this.loginVerwalter = loginVerwalter;
	}
	
}
