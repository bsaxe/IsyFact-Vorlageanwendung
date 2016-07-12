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
 * Diese Klasse enthält die Konstanten Fehlerschluessel
 * @author msg systems ag, Maximilian Falter
 *
 */

public abstract class FehlerSchluessel {
	
	/** Fehler beim Zugriff auf einen Zeitraum innerhalb einer Terminfindung. */
	public static final String MSG_ZEITRAUM_NICHT_GEFUNDEN = "TRMIN10010";
	
	/** Die übergebene Terminfindungsnummer {0} konnte keiner Terminfindung zugeordnet werden.*/
    public static final String MSG_TERMINFINDUNG_NICHT_GEFUNDEN = "TRMIN10011";
	
	/** Es ist ein technischer Fehler im Modul plisweb-gui aufgetreten: {0} . */
    public static final String MSG_ALLGEMEINER_TECHNISCHER_FEHER_MIT_PARAMETER = "TRMIN90000";

    /** Der übergebene Parameter {0} besitzt den ungültigen Wert {1}. */
    public static final String MSG_PARAMETER_UNGUELTIG = "TRMIN90001";

    /** Die im Parameter {0} übergebene Liste ist leer */
    public static final String MSG_LISTE_LEER = "TRMIN90002";

    /**
     * Der übergebene Parameter {0} darf nicht in der Zukunft liegen und besitzt den ungültigen Wert {1}.
     */
    public static final String MSG_PARAMETER_DATUM_ZUKUNFT = "TRMIN90003";

    /**Die übergebene Terminfindungsnummer {0} konnte nicht in einen Long-Wert konvertiert werden.*/
    public static final String MSG_TERMINFINDUNGSNR_NICHT_KONVERTIERBAR = "TRMIN91000";

    /**Es wurde keine Terminfindungsnummer gesetzt.*/
    public static final String MSG_KEINE_TERMINFINDUNGSNR = "TRMIN91001";

    /** Es ist ein technischer Fehler im Modul plisweb-gui aufgetreten. */
    public static final String MSG_ALLGEMEINER_TECHNISCHER_FEHLER = "TRMIN99999";
}
