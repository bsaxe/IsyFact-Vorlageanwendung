package de.msg.terminfindung.gui.terminfindung.teilnehmen;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.msg.terminfindung.common.exception.TerminfindungTechnicalException;
import de.msg.terminfindung.gui.terminfindung.model.PraeferenzModel;
import de.msg.terminfindung.persistence.entity.Praeferenz;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.gui.terminfindung.AbstractController;
import de.msg.terminfindung.gui.terminfindung.model.TeilnehmerModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;

/**
 * Controller für den Teilnahme Flow
 * 
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
@Controller
public class TeilnehmenController extends AbstractController<TeilnehmenModel> {

	private static final Logger LOG = Logger.getLogger(TeilnehmenController.class);
	
	/**
	 * Initialisiert das Model.
	 * 
	 * @param model Das Model
	 * @throws TerminfindungBusinessException 
	 */
	public void initialisiereModel(TeilnehmenModel model) throws TerminfindungTechnicalException{

		LOG.info("Initialisiere das Modell.");

		super.holeTerminfindung(model);
		aktualisiereModel(model);
	}
	
	/**
	 * Aktualisiert die abhängigen Daten im Model anhand der vorhandenen Terminfindung.
	 * Diese Methode ist vom Laden der Terminfindung getrennt, um ggf. bei 
	 * Änderungen am Terminfindungs-Attribut des Models die davon abhängigen Daten des
	 * Models neu berechnen zu können, ohne die Terminfindung wieder neu aus
	 * dem Anwendungskern zu laden.
	 * 
	 * @param model Das Model.
	 */
	public void aktualisiereModel (TeilnehmenModel model) {

		LOG.info("Initialisiere das Teilnehmen-Modell.");
		model.setTeilnehmerName("");
		model.getTage().clear();

		// initialisiere Datenstrukturen für die Darstellung:
		model.getZeitraumAuswahl().init(model.getTerminfindung().getTage());
	}
	

	/**
	 * Speichern eines neuen Teilnehmers und seiner Tag-Präferenzen.
	 * Ruft den AWK auf, um den Teilnehmer samt seiner Praeferenzen zu den
	 * Zeitraeumen in der DB zu speichern
	 * 
	 * @param model Das Modell
	 */
	public void speichereTeilnehmer(TeilnehmenModel model) {
		if (fuehreValidierungDurch(model)) {

			Map<ZeitraumModel, PraeferenzModel> terminwahl = new HashMap<>();

			for (ZeitraumModel zeitraum : model.getTerminfindung().getAlleZeitraeume()) {

				int ordinal = Integer.parseInt(String.valueOf(model.getZeitraumAuswahl().getCheckedRadio().get(zeitraum.getZeitraum_nr())));
				PraeferenzModel praeferenz = PraeferenzModel.values()[ordinal];

				terminwahl.put(zeitraum, praeferenz);
			}

			TeilnehmerModel teilnehmer = new TeilnehmerModel();
			teilnehmer.setName(model.getTeilnehmerName());
			try {
				model.setTerminfindung(super.getAwk().bestaetigeTeilnahme(model.getTerminfindung(), teilnehmer, terminwahl));
			}
			catch (TerminfindungBusinessException e) {
				LOG.error("Fehler beim Speichern des Teilnehmers: " + e.getMessage());
			}
		}
	}

	/**
	 * Validierung der eingegebenen Daten vor dem Speichern.
	 * Es wird überprüft, ob der Namen des Teilnehmers ausgefüllt wurde und
	 * dass er sich von den schon vorhandenen Namen unterscheidet.
	 *
	 * @param model Das Modell
	 * @return true, wenn die Validierung erfolgreich war, sonst false
	 */
	public boolean fuehreValidierungDurch(TeilnehmenModel model) {
		List<ValidationMessage> validationMessages = new ArrayList<>();
		// Der Name des Teilnehmers darf nicht leer sein
		if (model.getTeilnehmerName().equals("")) {
			validationMessages.add(new ValidationMessage("NA",
					"teilnehmerName", "Ihr Name", "Bitte Namen eingeben"));
		}
		// Der Name des Teilnehmers darf noch nicht vergeben sein.
		if (model.getTerminfindung().existsTeilnehmerName(model.getTeilnehmerName())) {
			validationMessages.add(new ValidationMessage("NA",
					"teilnehmerName", "Ihr Name",
					"Teilnehmer existiert bereits"));
		}

		this.globalFlowController.getValidationController().processValidationMessages(validationMessages);

		// Wenn die Liste der Messages leer ist, gab es keine Validierungsfehler,
		// Gib in diesem Fall true zurück
		return (validationMessages.isEmpty());
	}

	/**
	 * Legt fuer den Teilnehmer alle Praeferenzen auf false und ruft
	 * anschließend speichereTeilnehmer auf
	 * 
	 * @param model Das Modell
	 */
	public void speichereTeilnehmerAllFalse(TeilnehmenModel model) {
		if (fuehreValidierungDurch(model)) {
			for (Map.Entry<Long, Integer> entry : model.getZeitraumAuswahl().getCheckedRadio().entrySet()) {
				entry.setValue(0);
			}
			speichereTeilnehmer(model);
		}
	}
}

