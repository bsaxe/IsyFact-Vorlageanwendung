package de.msg.terminfindung.gui.util;

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

import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * Holder für eine Terminfindungsnummer, die als Request-Parameter übergeben wurde.
 * Der Holder wird in die Controller injected. Auf den gespeicherten Wert
 * hat der Controller, unabhängig vom Model, Zugriff. Ein einmal gespeicherter Wert
 * bleibt erhalten, bis er von einem neuen Wert überschrieben wird.
 *
 * @author msg systems ag, Dirk Jäger
 */
public class TFNumberHolder implements Serializable{

    private static final Logger LOG = Logger.getLogger(TFNumberHolder.class);

    /** Die gespeicherte Terminfindungsnummer */
    private Long number = null;

    public TFNumberHolder() {}
    public TFNumberHolder(Long number) { this.number = number; }
    public Long getNumber() { return number; }

    public void updateIfNotNull (String tfNumberString) throws TerminfindungTechnicalException{

        if (tfNumberString != null) {
            // Wenn der übergebene String nicht null ist, wird versucht, ihn in einen Long Wert
            // zu parsen und diesen als neue Terminfindungsnummer im Holder abzuspeichern

            LOG.debug("Update der TF-Nummer : " + tfNumberString);
            long terminfindungsNr;
            try {
                terminfindungsNr = Long.parseLong(tfNumberString);
            }
            catch (NumberFormatException e) {
                LOG.error("NumberFormatException beim Parsen von: " + tfNumberString);
                throw new TerminfindungTechnicalException(FehlerSchluessel.MSG_TERMINFINDUNGSNR_NICHT_KONVERTIERBAR, e, tfNumberString);
            }
            this.number = terminfindungsNr;
        }
        else {
            // Nichts tun, der bisher gespeicherte Wert bleibt gespeichert

            LOG.debug("Update angefordert für TF-Nummer ist null, behalte gespeicherten Wert " + number);
        }
    }
}
