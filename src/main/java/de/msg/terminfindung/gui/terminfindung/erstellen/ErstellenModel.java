package de.msg.terminfindung.gui.terminfindung.erstellen;

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


import de.msg.terminfindung.gui.terminfindung.AbstractModel;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;
import de.msg.terminfindung.gui.util.DataGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model des Erstellen Flows
 *
 * @author msg systems ag, Dirk Jäger
 */

public class ErstellenModel extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1653204808232247581L;

    /**
     * Speichert den Namen der Veranstaltungen aus der Eingabemaske
     **/
    private String name;
    /**
     * Speichert den Namen der Organisation aus der Eingabemaske
     **/
    private String orgName;
    /**
     * Speichert eine neu eingegebenes Datum als String
     */
    private String stringTempDate = "";
    /**
     * Stellt den Platzhalterwert bereit, der im Datums-Eingabefeld angezeigt wird
     */
    private String stringPlaceholderDate = "";
    /**
     * Speicher einen Tag, der in der Eingabemaske durch Klicken auf das Trash-Icon zum Löschen ausgewählt wurde
     */
    private TagModel selectedTermin;
    /**
     * Speicher einen Zeitraum, der in der Eingabemaske durch Klicken auf das Trash-Icon zum Löschen ausgewählt wurde
     */
    private ZeitraumModel selectedZeitraum;
    /**
     * Zur Auswahl stehende mögliche Zeiträume
     */
    private List<String> alleZeitraeume = DataGenerator.getUhrzeitAuswahl();

    private List<TagModel> tage = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public TagModel getSelectedTermin() {
        return selectedTermin;
    }

    public void setSelectedTermin(TagModel selectedTermin) {
        this.selectedTermin = selectedTermin;
    }

    public String getStringTempDate() {
        return stringTempDate;
    }

    public void setStringTempDate(String stringTempDate) {
        this.stringTempDate = stringTempDate;
    }

    public ZeitraumModel getSelectedZeitraum() {
        return selectedZeitraum;
    }

    public void setSelectedZeitraum(ZeitraumModel selectedZeitraum) {
        this.selectedZeitraum = selectedZeitraum;
    }

    public List<String> getAlleZeitraeume() {
        return alleZeitraeume;
    }

    public String getStringPlaceholderDate() {
        return stringPlaceholderDate;
    }

    public void setStringPlaceholderDate(String stringPlaceholderDate) {
        this.stringPlaceholderDate = stringPlaceholderDate;
    }

    public List<TagModel> getTage() {
        return tage;
    }

    public void setTage(List<TagModel> termine) {
        this.tage = termine;
    }

}
