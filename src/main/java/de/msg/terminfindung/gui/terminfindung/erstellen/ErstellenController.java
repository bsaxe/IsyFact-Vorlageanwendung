package de.msg.terminfindung.gui.terminfindung.erstellen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.gui.terminfindung.AbstractController;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.TerminfindungModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;
import de.msg.terminfindung.gui.util.DataGenerator;
import de.msg.terminfindung.gui.util.DateUtil;

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
 * Controller für den Flow "Erstellen": Erstellung einer neuen Terminfindung
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
@Controller
public class ErstellenController extends AbstractController<ErstellenModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(ErstellenController.class);

    public void initialisiereModel(ErstellenModel model) {
        super.initialisiereModell(model);

        // Platzhalter für Anzeige in Datums-Eingabefeld
        model.setPlaceholderDate(DateUtil.format(DateUtil.getNDaysFromToday(1)));

        if (model.isTestMode()) {
            LOG.debug("TestMode: Erzeuge Tage");
            model.setTage(DataGenerator.generateTage(getKonfiguration()));
            model.setName("Test-Veranstaltung");
            model.setOrgName("Test-Organisation");
        }
    }  

    /**
     * Fügt einen Tag zur Liste der Tage hinzu.
     *
     * @param model Das Modell
     */
    public void fuegeDatumHinzu(ErstellenModel model) {

        List<ValidationMessage> validationMessages = new ArrayList<>();

        if (istValideEingabe(model, validationMessages))
        {
        	Date neuesDatum = model.getNewDate();
        	fuegeDatumZuModelHinzu(neuesDatum, model);
        }
        else
        {
        	globalFlowController.getValidationController().processValidationMessages(validationMessages);
        }
    }
    
    private void fuegeDatumZuModelHinzu(Date datum, ErstellenModel model)
    {
        LOG.debug("Füge Tag hinzu.");
        TagModel tag = new TagModel();
        tag.setDatum(datum);
        tag.setVonZeitraum(getKonfiguration().getAsString("termin.start.vorgabe"));
        tag.setBisZeitraum(getKonfiguration().getAsString("termin.ende.vorgabe"));
        model.getTage().add(tag);
        Collections.sort(model.getTage());
    }
    
    private boolean istValideEingabe(ErstellenModel model, List<ValidationMessage> validationMessages)
    {
    	if (maxAnzahlTageUeberschritten(model)) {
            validationMessages.add(new ValidationMessage("DA",
                    "newDate", "Datum",
                    "Bereits max. Anzahl an Daten hinzugefügt"));
            return false;
        }
        else if (keineEingabe(model)) {
            validationMessages.add(new ValidationMessage("DA",
                    "newDate", "Datum", "Benötigtes Feld"));
            return false;
        }
    	
	 	Date addedDate = model.getNewDate();
        if (datumLiegtInVergangenheit(addedDate)) {
            validationMessages
                    .add(new ValidationMessage("DA", "newDate",
                            "Datum",
                            "Das Datum darf nicht in der Vergangenheit liegen"));
            return false;
        }
        else if (datumBereitsVorhanden(model, addedDate)) {
            validationMessages.add(new ValidationMessage("DA",
                    "newDate", "Datum",
                    "Datum bereits hinzugefügt"));
            return false;
        }
    	
    	return true;
    }
    
    private boolean maxAnzahlTageUeberschritten(ErstellenModel model)
    {
    	return model.getTage().size() >= getKonfiguration().getAsInteger("termin.tag.max.number");
    }
    
    private boolean keineEingabe(ErstellenModel model)
    {
    	return model.getNewDate() == null || model.getNewDate().equals("");
    }
    
    private boolean datumLiegtInVergangenheit(Date date)
    {
    	return !DateUtil.getYesterday().before(date);
    }
    
    private boolean datumBereitsVorhanden(ErstellenModel model, Date date)
    {
    	return DateUtil.containsDay(model.getTage(), date);
    }

    /**
     * Loescht einen Tag aus der lokalen Liste der Tage (Daten)
     *
     * @param model Das Modell
     */
    public void loescheDatum(ErstellenModel model) {
        model.getTage().remove(model.getSelectedTermin());
    }

    /**
     * Fügt einen Zaitraum zu der lokalen Liste der Zeitraeume (Daten)
     *
     * @param model Das Modell
     */
    public void fuegeZeitraumHinzu(ErstellenModel model) {

        List<ValidationMessage> validationMessages = new ArrayList<>();

        boolean zeitraumExists = false;
        for (ZeitraumModel zeitraumModel : model.getSelectedTermin().getZeitraeume()) {
            if (zeitraumModel.getBeschreibung().equalsIgnoreCase(model.getSelectedTermin().getVonZeitraum() + " - "
                    + model.getSelectedTermin().getBisZeitraum())) {
                zeitraumExists = true;
            }
        }
        // maximale Anzahl von Tagen schon vorhanden?
        if (model.getSelectedTermin().getZeitraeume().size() >= getKonfiguration().getAsInteger("termin.tag.zeitraum.max.number")) {
            validationMessages.add(new ValidationMessage("DA",
            		"zeitraeume_" + model.getSelectedTermin().getShortDate(), "Zeitraum",
                    "Bereits max. Anzahl an Daten hinzugefügt"));
        }
        else if (model.getSelectedTermin().getVonZeitraum().compareTo(model.getSelectedTermin().getBisZeitraum()) == 0) {
            validationMessages.add(new ValidationMessage("DA",
                    "zeitraeume_" + model.getSelectedTermin().getShortDate(), "Zeitraum",
                    "Zeitraum beginnt und Enden um die gleiche Uhrzeit."));
        } else if (model.getSelectedTermin().getVonZeitraum().compareTo(model.getSelectedTermin().getBisZeitraum()) > 0) {
            validationMessages.add(new ValidationMessage("DA",
                    "zeitraeume_" + model.getSelectedTermin().getShortDate(), "Zeitraum",
                    "Zeitraum startet nach seinem Ende."));
        } else if (zeitraumExists) {
            validationMessages.add(new ValidationMessage("DA",
                    "zeitraeume_" + model.getSelectedTermin().getShortDate(), "Zeitraum",
                    "Zeitraum existiert bereits."));
        } else {
            ZeitraumModel zeitraum = new ZeitraumModel();
            zeitraum.setBeschreibung(model.getSelectedTermin().getVonZeitraum() + " - " + model.getSelectedTermin().getBisZeitraum());
            model.getSelectedTermin().getZeitraeume().add(zeitraum);
            model.getSelectedTermin().setVonZeitraum(getKonfiguration().getAsString("termin.start.vorgabe"));
            model.getSelectedTermin().setBisZeitraum(getKonfiguration().getAsString("termin.ende.vorgabe"));
            Collections.sort(model.getSelectedTermin().getZeitraeume());
            return;
        }

        this.globalFlowController.getValidationController().processValidationMessages(validationMessages);
    }

    /**
     * Löscht einen Zeitraum aus der lokalen Liste der Zeitraeume (Daten)
     *
     * @param model Das Modell
     */
    public void loescheZeitraum(ErstellenModel model) {
        for (TagModel tag : model.getTage()) {
            tag.getZeitraeume().remove(model.getSelectedZeitraum());
        }
    }

    /**
     * Validiert das Modell vor dem Speichern. Es wird geprüft, ob die Pflichtfelder gefüllt sind.
     *
     * @param model Das Modell
     * @return true wenn alle Pflichtfelder gefüllt sind, sonst false
     */
    public boolean validiereStammdaten(ErstellenModel model) {
        List<ValidationMessage> validationMessages = new ArrayList<>();
        if (StringUtils.isBlank(model.getName())) {
            validationMessages.add(new ValidationMessage("TI", "name", "Titel", "Benötigtes Feld"));
        }
        if (StringUtils.isBlank(model.getOrgName())) {
            validationMessages.add(new ValidationMessage("NA", "orgName", "Ihr Name", "Benötigtes Feld"));
        }

        if (validationMessages.isEmpty()) {
            return true;
        } else {
            globalFlowController.getValidationController().processValidationMessages(validationMessages);
            return false;
        }
    }

    /**
     * Validiert das Modell vor dem Speichern. Es wird geprüft ob alle Tage mindestens einen Zeitraum beinhalten. Falls
     * das Modell konsistent ist, wird es gespeichert.
     */
    public boolean validiereTermine(ErstellenModel model) {
        List<ValidationMessage> validationMessages = new ArrayList<>();

        for (TagModel tag : model.getTage()) {
            if (tag.getZeitraeume().isEmpty()) {
                validationMessages.add(new ValidationMessage("DA",
                        "zeitraeume", "Datum",
                        "Dem Datum " + tag.getShortDate() + " ist kein Zeitraum zugeordnet."));
            }
        }

        if (validationMessages.isEmpty()) {
            return speichereModel(model);
        } else {
            globalFlowController.getValidationController().processValidationMessages(validationMessages);
            return false;
        }
    }

    /**
     * Speichert die neu angelegte Terminfindung. Die Methode ruft dazu den Wrapper des Anwendungskerns mit den Daten
     * auf, die im Modell vorliegen.
     *
     * @param model Das Modell, dessen Inhalt gespeichert wird
     */
    private boolean speichereModel(ErstellenModel model) {

        LOG.debug("Speichere Terminfindung.");

        try {
            TerminfindungModel terminfindung = getAwk().erstelleTerminfindung(model.getOrgName(), model.getName(), model.getTage());
            model.setTerminfindung(terminfindung);
            return true;
        } catch (TerminfindungBusinessException e) {
            LOG.errorFachdaten(e.getAusnahmeId() , "Fehler beim Erstellen der Terminfindung", e);
            return false;
        }
    }
}
