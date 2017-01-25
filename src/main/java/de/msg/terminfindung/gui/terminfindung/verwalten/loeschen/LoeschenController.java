package de.msg.terminfindung.gui.terminfindung.verwalten.loeschen;

import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.gui.terminfindung.AbstractController;
import de.msg.terminfindung.gui.terminfindung.model.TerminfindungModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

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
 * Controller fuer den Loeschen Flow
 * 
 * @author msg systems ag, Maximilian Falter 
 */
@Controller
public class LoeschenController extends AbstractController<LoeschenModel> {

	private static final IsyLogger LOG = IsyLoggerFactory.getLogger(LoeschenController.class);

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

		List<ZeitraumModel> viewZeitraumList = new ArrayList<>();
		for (Long zeitraum_nr : model.getCheckedByUser().keySet())  {
			if (model.getCheckedByUser().get(zeitraum_nr)) {
				LOG.trace("Is  checked : {}", zeitraum_nr);
				viewZeitraumList.add(model.getTerminfindung().findeZeitraumById(zeitraum_nr));
			}
			else {
				LOG.trace("Not checked : {}", zeitraum_nr);
			}
		}

		try {
			TerminfindungModel terminfindung = super.getAwk().loescheZeitraeume(model.getTerminfindung(), viewZeitraumList);
			model.setTerminfindung(terminfindung);

		} catch (TerminfindungBusinessException e) {

			LOG.error(e.getAusnahmeId(), "Fehler beim Löschen der Terminfindung: ",e);
		}
	}
}
