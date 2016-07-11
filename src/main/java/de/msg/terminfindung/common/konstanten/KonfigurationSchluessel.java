package de.msg.terminfindung.common.konstanten;

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

/**
 * Diese Klasse enthält Konstanten, die zum Lesen der Konfiguration benötigt werden.
 *
 * @author Stefan Dellmuth, msg systems
 */
public abstract class KonfigurationSchluessel {

    /**
     * Organisation des Batchnutzers
     */
    public static final String BATCH_ORGANISATION = "batch.organisation";

    /**
     * Name des Batchnutzers
     */
    public static final String BATCH_BENUTZER = "batch.benutzer";

    /**
     * Passwort des Batchnutzers
     */
    public static final String BATCH_PASSWORT = "batch.passwort";

    /**
     * Schalter, ob der Batch im Testmodus läuft
     */
    public static final String BATCH_TESTMODUS = "testmodus";

    /**
     * Frist für das Löschen vergangener Terminfindungen (in Tagen)
     */
    public static final String BATCH_FRIST_LOESCHEN = "batch.loeschen.frist";

}
