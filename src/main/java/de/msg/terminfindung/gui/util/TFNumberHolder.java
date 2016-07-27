package de.msg.terminfindung.gui.util;

import java.io.Serializable;
import java.util.UUID;

import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;

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

import de.msg.terminfindung.common.exception.TerminfindungTechnicalException;
import de.msg.terminfindung.common.konstanten.FehlerSchluessel;

/**
 * Holder für eine Terminfindungsnummer, die als Request-Parameter übergeben
 * wurde. Der Holder wird in die Controller injected. Auf den gespeicherten Wert
 * hat der Controller, unabhängig vom Model, Zugriff. Ein einmal gespeicherter
 * Wert bleibt erhalten, bis er von einem neuen Wert überschrieben wird.
 *
 * @author msg systems ag, Dirk Jäger
 */
public class TFNumberHolder implements Serializable {
	private static final long serialVersionUID = -8673855660241394242L;

	private static final IsyLogger LOG = IsyLoggerFactory.getLogger(TFNumberHolder.class);

	/** Die gespeicherte Referenz der Terminfindung */
	private UUID ref = null;

	public TFNumberHolder() {
	}

	public UUID getRef() {
		return ref;
	}

	public void updateRefIfNotNull(UUID u) {

		if (u != null) {
			LOG.debug("Update der TF-Referenz : " + u);
			ref = u;
		} else {
			LOG.debug("Update angefordert für TF-Referenz ist null, behalte gespeicherten Wert {}", ref);
		}
	}
}
