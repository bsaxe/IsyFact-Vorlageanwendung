package de.msg.terminfindung.gui.terminfindung.verwalten.abschliessen;

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


import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.exception.TerminfindungTechnicalException;
import de.msg.terminfindung.gui.awkwrapper.AwkWrapper;
import de.msg.terminfindung.gui.terminfindung.AbstractController;
import de.msg.terminfindung.gui.terminfindung.model.ViewTerminfindung;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

/**
 * Controller fuer den Abschliessen Flow
 * 
 * @author msg systems ag, Maximilian Falter 
 */
@Controller
public class AbschliessenController extends AbstractController<AbschliessenModel> {

	private static final Logger LOG = Logger.getLogger(AbschliessenController.class);

	/**
	 * Diese Methode initialisiert das Model, d.h es wird zum einen, falls es
	 * bereits existiert, geleert und zum anderen mit den Daten aus der
	 * Datenbank gefüllt.
	 * @throws TerminfindungBusinessException 
	 */
	public void initialisiereModel(AbschliessenModel model) throws TerminfindungTechnicalException {

		LOG.info("Initialisiere das Modell.");
		model.getTage().clear();

		super.holeTerminfindung(model);
	}


	/**
	 * Ruft den AWK auf, um einen definitiven Zeitraum (Tag) fuer die
	 * Terminfindung festzulegen, zusaetzlich wird noch im Model die Variable
	 * istAbgeschlossen auf true gesetzt, damit keine Aktionen, welche die
	 * Terminfindung manipulieren koennten, ausfuehrbar sind.
	 * 
	 * @param model Das Modell
	 */
	public void schliesseTerminfindung(AbschliessenModel model) {

		LOG.info("Schließe die Terminfindung ab.");

		AwkWrapper awk = super.getAwk();

		long zeitraumNr = model.getSelectedZeitraumNr();

		try {
			ViewTerminfindung terminfindung = awk.setzeVeranstaltungstermin(model.getTerminfindung(), zeitraumNr);
			model.setTerminfindung(terminfindung);		
		}
		catch (TerminfindungBusinessException e) {
				LOG.error("Fehler beim Abschluss der Terminfindung: " + e.getMessage());
		}
	}

	public String getClassForPraeferenz(Byte praeferenzWert) {

		switch (praeferenzWert) {
		case 0:
			return "tdFalse";
		case 1:
			return "tdTrue";
		case 2:
			return "tdHaveTo";
		default:
			return "";
		}
	}
}
