package de.msg.terminfindung.gui.terminfindung.verwalten.loeschen;

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
import de.msg.terminfindung.gui.terminfindung.model.ViewZeitraum;
import de.msg.terminfindung.persistence.entity.Zeitraum;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import de.msg.terminfindung.gui.terminfindung.AbstractController;
import de.msg.terminfindung.gui.terminfindung.model.ViewTerminfindung;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller fuer den Loeschen Flow
 * 
 * @author msg systems ag, Maximilian Falter 
 */
@Controller
public class LoeschenController extends AbstractController<LoeschenModel> {

	private static final Logger LOG = Logger.getLogger(LoeschenController.class);

	/**
	 * Initialisiert das Model mit einer vorgegebenen Terminfindung.
	 * 
	 * @param model Das Model
	 * @throws TerminfindungBusinessException 
	 */
	public void initialisiereModel(LoeschenModel model) throws TerminfindungTechnicalException {

		LOG.info("Initialisiere das Modell.");
		super.holeTerminfindung(model);
	}

	/**
	 * Setzt die Datenstruktur für die Auswahl der zu löschenden Zeiträume zurück.
	 * Diese Methode wird aus dem Flow aufgerufen, um sicherzustellen, dass die
	 * Datenstruktur beim wiederholtem Aufruf des Views immer leer.
	 *
	 * @param model Das Model
	 */
	public void setzeAuswahlZurueck(LoeschenModel model) {
		model.getCheckedByUser().clear();
	}

	/**
	 * Loescht die vom Benutzer in der GUI ausgewaehlten Zeitraeume.
	 * 
	 * @param model Das Modell
	 */
	public void loescheZeitraeume (LoeschenModel model) {

		List<ViewZeitraum> viewZeitraumList = new ArrayList<>();
		for (Long zeitraum_nr : model.getCheckedByUser().keySet())  {
			if (model.getCheckedByUser().get(zeitraum_nr)) {
				LOG.debug("Is  checked :" + zeitraum_nr);
				viewZeitraumList.add(model.getTerminfindung().findeZeitraumById(zeitraum_nr));
			}
			else {
				LOG.debug("Not checked :" + zeitraum_nr);
			}
		}

		try {
			ViewTerminfindung terminfindung = super.getAwk().loescheZeitraeume(model.getTerminfindung(), viewZeitraumList);
			model.setTerminfindung(terminfindung);

		} catch (TerminfindungBusinessException e) {

			LOG.error("Fehler beim Löschen der Terminfindung: " + e.getMessage());
		}
	}
}
