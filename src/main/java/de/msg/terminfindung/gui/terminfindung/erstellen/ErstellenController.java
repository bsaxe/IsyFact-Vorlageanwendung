package de.msg.terminfindung.gui.terminfindung.erstellen;

import java.text.DateFormat;

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


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.msg.terminfindung.gui.util.DataGenerator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.gui.terminfindung.AbstractController;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.TerminfindungModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;
import de.msg.terminfindung.gui.util.DateUtil;

/**
 * Controller für den Flow "Erstellen":
 * Erstellung einer neuen Terminfindung
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
@Controller
public class ErstellenController extends AbstractController<ErstellenModel> {

	private static final Logger LOG = Logger.getLogger(ErstellenController.class);	
	
	/** Die maximale Anzahl von Tagen, die eine Terminfindung enthalten kann */
	public final int MAX_NUMBER_OF_DAYS = 10;

	/** Die Anzahl der Zeiträume, die ein Benutzer pro Tag angeben kann */
	public final int ZEITRAEUME_PRO_TAG = 3;

	public void initialisiereModel(ErstellenModel model) {
		super.initialisiereModell(model);

		// Platzhalter für Anzeige in Datums-Eingabefeld
		model.setStringPlaceholderDate(DateUtil.format(DateUtil.getNDaysFromToday(1)));

		if (model.isTestMode()) {
			LOG.debug("TestMode: Erzeuge Tage");
			model.setTage(DataGenerator.generateTage());
			model.setName("Test-Veranstaltung");
			model.setOrgName("Test-Organisation");
		}
	}

	/**
	 * Fügt einen Tag zur Liste der Tage hinzu.
	 * 
	 * @param model Das Modell
	 */
	public void addDatum(ErstellenModel model) {

		Date addedDate;
		List<ValidationMessage> validationMessages = new ArrayList<>();

		// maximale Anzahl von Tagen schon vorhanden?
		if (model.getTage().size() >= MAX_NUMBER_OF_DAYS) {
			validationMessages.add(new ValidationMessage("DA",
					"stringTempDate", "Datum",
					"Bereits max. Anzahl an Daten hinzugefügt"));
		}
		// keine Eingabe vorhanden
		else if (model.getStringTempDate() == null || model.getStringTempDate().equals("")) {
			validationMessages.add(new ValidationMessage("DA",
					"stringTempDate", "Datum", "Benötigtes Feld"));
		}
		// konvertiere in Datumsobjekt für weitere Prüfungen
		else try {
			addedDate = DateUtil.convertDate(model.getStringTempDate());
				// Datum darf nicht in der Vergangenheit liegen
			if (!DateUtil.getYesterday().before(addedDate)) {
				validationMessages
						.add(new ValidationMessage("DA", "stringTempDate",
								"Datum",
								"Das Datum darf nicht in der Vergangenheit liegen"));
			}
			// Datum darf nicht schon vorhanden sein
			else if (DateUtil.containsDay(model.getTage(), addedDate)) {
				validationMessages.add(new ValidationMessage("DA",
						"stringTempDate", "Datum",
						"Datum bereits hinzugefügt"));
			}
			// alle Tests bis hierhin bestanden, dann füge neuen Tag hinzu.
			else {
				LOG.debug("Füge Tag hinzu.");
				// erzeuge ein neues Objekt für den Tag
				TagModel tag = new TagModel();
				// setze in dem neuen Objekt die Zeitraum-Objekte
				//erzeugeZeitraeume(tag);
				// setze das Datum
				tag.setDatum(addedDate);
				// füge den Tag zum Model hinzu
				model.getTage().add(tag);
				Collections.sort(model.getTage());
				return;
			}

		} catch (ParseException pe) {
			validationMessages.add(new ValidationMessage("DA",
					"stringTempDate", "Datum",
					"Geben Sie ein gültiges Datum ein"));
		}
		this.globalFlowController.getValidationController().processValidationMessages(validationMessages);
	}

	/**
	 * Speichert die neu angelegte Terminfindung.
     * Die Methode ruft dazu den Wrapper des Anwendungskerns mit den Daten auf,
     * die im Modell vorliegen.
	 * 
	 * @param model Das Modell, dessen Inhalt gespeicehrt wird
	 */
	public void speichereModel(ErstellenModel model) {

		LOG.debug("Speichere Terminfindung.");
		
		try {
			TerminfindungModel terminfindung = super.getAwk().erstelleTerminfindung(model.getOrgName(), model.getName(), model.getTage());
			model.setTerminfindung(terminfindung);
		} catch (TerminfindungBusinessException e) {	
			LOG.error("Fehler beim Erstellen der Terminfindung: " + e.getMessage());
		}
	}

	/**
	 * Loescht einen Tag aus der lokalen Liste der Tage (Daten)
	 * 
	 * @param model Das Modell
	 */
	public void delDatum(ErstellenModel model) {
		model.getTage().remove(model.getSelectedTermin());
	}
	
	/**
	 * Loescht einen Zaitraum aus der lokalen Liste der Zeitraeume (Daten)
	 * @param model Das Modell
	 */
	public void delZeitraum(ErstellenModel model){
		for (TagModel tag : model.getTage()) {
			tag.getZeitraeume().remove(model.getSelectedZeitraum());
		}
	}
	
	/**
	 * Fügt einen Zaitraum zu der lokalen Liste der Zeitraeume (Daten)
	 * @param model Das Modell
	 */
	public void addZeitraum(ErstellenModel model){
		
		List<ValidationMessage> validationMessages = new ArrayList<>();
		
		boolean zeitraumExists = false;
		for (ZeitraumModel zeitraumModel : model.getSelectedTermin().getZeitraeume()) {
			if(zeitraumModel.getBeschreibung().equalsIgnoreCase(model.getSelectedTermin().getVonZeitraum() + " - " 
					+ model.getSelectedTermin().getBisZeitraum())){
				zeitraumExists = true;
			}
		}
		
		if(model.getSelectedTermin().getVonZeitraum().compareTo(model.getSelectedTermin().getBisZeitraum()) == 0){
			validationMessages.add(new ValidationMessage("DA",
					"zeitraeume_" + model.getSelectedTermin().getShortDate(), "Zeitraum",
					"Zeitraum beginnt und Enden um die gleiche Uhrzeit."));
		}
		else if(model.getSelectedTermin().getVonZeitraum().compareTo(model.getSelectedTermin().getBisZeitraum()) > 0){
			validationMessages.add(new ValidationMessage("DA",
					"zeitraeume_" + model.getSelectedTermin().getShortDate(), "Zeitraum",
					"Zeitraum startet nach seinem Ende."));
		}
		else if(zeitraumExists){
			validationMessages.add(new ValidationMessage("DA",
					"zeitraeume_" + model.getSelectedTermin().getShortDate(), "Zeitraum",
					"Zeitraum existiert bereits."));
		}
		else{
			ZeitraumModel zeitraum = new ZeitraumModel();
			zeitraum.setBeschreibung(model.getSelectedTermin().getVonZeitraum() + " - " + model.getSelectedTermin().getBisZeitraum());
			model.getSelectedTermin().getZeitraeume().add(zeitraum);
			Collections.sort(model.getSelectedTermin().getZeitraeume());
			return;
		}
		
		this.globalFlowController.getValidationController().processValidationMessages(validationMessages);
	}

	/**
	 * Validiert das Modell vor dem Speichern.
     * Es wird geprüft, ob die Pflichtfelder gefüllt sind.
     *
	 * @param model Das Modell
	 * @return true wenn alle Pflichtfelder gefüllt sind, sonst false
	 */
	public boolean validiereEingabefelder (ErstellenModel model) {
		List<ValidationMessage> validationMessages = new ArrayList<>();
		if (model.getName().equals("")) {
			validationMessages.add(new ValidationMessage("TI", "name",
					"Titel", "Benötigtes Feld"));
		}
		if (model.getOrgName().equals("")) {
			validationMessages.add(new ValidationMessage("NA", "orgName",
					"Ihr Name", "Benötigtes Feld"));
		}

		this.globalFlowController.getValidationController().processValidationMessages(validationMessages);

		// Wenn die Liste der Message leer ist, gab es keine Validierungsfehler,
		// Gib in diesem Fall true zurück
		return (validationMessages.isEmpty());
	}
	
	/**
	 * Validiert das Modell vor dem Speichern. Es wird geprüft ob alle Tage mindestens einen Zeitraum beinhalten.
	 * Falls das Modell konsistent ist, wird es gespeichert.
	 */
	public boolean validiereErstellenModel(ErstellenModel model){
		List<ValidationMessage> validationMessages = new ArrayList<>();
		
		for (TagModel tag : model.getTage()) {
			if(tag.getZeitraeume().isEmpty()){
				validationMessages.add(new ValidationMessage("DA",
						"zeitraeume", "Datum",
						"Dem Datum "+ tag.getShortDate() +" ist kein Zeitraum zugeordnet."));
			}
		}
		
		if(validationMessages.isEmpty()){
			speichereModel(model);
		}
		else{
			this.globalFlowController.getValidationController().processValidationMessages(validationMessages);
		}

		// Wenn die Liste der Message leer ist, gab es keine Validierungsfehler,
		// Gib in diesem Fall true zurück
		return (validationMessages.isEmpty());
	}

    /**
     * Erzeugt die Default-Anzahl von Zeiträumen pro Tag.
     * Die Methode wird beim Anlegen eines neuen Tages intern aufgerufen.
     * @param tag Eine Referenz auf das {@link TagModel} Objekt, in dem die Zeiträume angelegt werden.
     */
    private void erzeugeZeitraeume (TagModel tag) {

		for (int i = 0; i <= ZEITRAEUME_PRO_TAG; i++) {
			tag.getZeitraeume().add(new ZeitraumModel());
		}
	}
}
