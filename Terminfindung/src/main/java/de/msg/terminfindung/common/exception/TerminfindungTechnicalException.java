package de.msg.terminfindung.common.exception;

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


import de.bund.bva.pliscommon.exception.FehlertextProvider;
import de.bund.bva.pliscommon.exception.PlisTechnicalException;
import de.bund.bva.pliscommon.util.exception.MessageSourceFehlertextProvider;

/**
 * Klasse für technische Exceptions.
 *
 * @author msg systems ag, Dirk Jäger
 */
public class TerminfindungTechnicalException extends PlisTechnicalException {

    /**
     * Die Serial-Version UID.
     */
    private static final long serialVersionUID = 1L;


    /** Fehlertextprovider zum Auslesen von Fehlertexten. */
    private static final FehlertextProvider FEHLERTEXT_PROVIDER = new MessageSourceFehlertextProvider();

    /**
     * Konstruktor.
     * @param ausnahmeId
     *            Ausnahme-Id
     * @param parameter
     *            die Parameter
     */
    public TerminfindungTechnicalException (String ausnahmeId, String... parameter) {
        super(ausnahmeId, FEHLERTEXT_PROVIDER, parameter);
    }

    /**
     * Konstruktor.
     * @param ausnahmeId
     *            Ausnahme-Id
     * @param throwable
     *            Throwable
     * @param parameter
     *            die Parameter
     */
    public TerminfindungTechnicalException (String ausnahmeId, Throwable throwable, String... parameter) {
        super(ausnahmeId, throwable, FEHLERTEXT_PROVIDER, parameter);
    }
}
