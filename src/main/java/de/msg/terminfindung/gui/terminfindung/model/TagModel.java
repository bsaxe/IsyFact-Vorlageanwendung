package de.msg.terminfindung.gui.terminfindung.model;

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


import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Die Klasse speichert die Daten eines Tags in der View Schicht. Ein Tag hat einen Namen, ein Datum und eine Liste von
 * Zeiträumen an diesem Datum.
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
public class TagModel implements Serializable, Comparable<TagModel> {

    private static final long serialVersionUID = -5975535556486949171L;

    private Long id;
    private Date datum;
    private List<ZeitraumModel> zeitraeume = new ArrayList<>();

    /**
     * der Ausgewählte Zeitraum von
     */
    private String vonZeitraum;
    /**
     * der Ausgewählte Zeitraum bis
     */
    private String bisZeitraum;

    public TagModel() {

    }

    public TagModel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long tag_nr) {
        this.id = tag_nr;
    }

    public Date getDatum() {
        return datum;
    }

    public String getShortDate() {
        return DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY).format(datum);
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getAnzahlZeitraeume() {
        return zeitraeume.size();
    }

    public List<ZeitraumModel> getZeitraeume() {
        return zeitraeume;
    }

    public void setZeitraeume(List<ZeitraumModel> zeitraeume) {
        this.zeitraeume = zeitraeume;
    }

    @Override
    public int compareTo(TagModel tag) {
        return this.getDatum().compareTo(tag.getDatum());

    }

    public String getVonZeitraum() {
        return vonZeitraum;
    }

    public void setVonZeitraum(String vonZeitraum) {
        this.vonZeitraum = vonZeitraum;
    }

    public String getBisZeitraum() {
        return bisZeitraum;
    }

    public void setBisZeitraum(String bisZeitraum) {
        this.bisZeitraum = bisZeitraum;
    }
}
