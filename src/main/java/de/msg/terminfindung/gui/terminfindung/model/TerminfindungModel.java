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


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Die Klasse speichert eine Terminfindung in der View-Schicht.
 *
 * @author msg systems ag, Dirk Jäger
 */

public class TerminfindungModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Die Nummer (Id) der Terminfindung
     */
    private long id;

    /**
     * Der vom Veranstalter letztlich ausgewählte Termin der Veranstaltung. Dieser Termin (=Zeitraum an einem Tag) wird
     * bei Abschluss der Terminfindung gesetzt.
     */
    private ZeitraumModel defZeitraum;

    /**
     * Die Liste der zur Auswahl stehenden Tage. Jeder Tag enthält mindestens einen Zeitraum an diesem Tag.
     */
    private List<TagModel> tage = new ArrayList<>();

    /**
     * Die Liste der Teilnehmer.
     */
    private List<TeilnehmerModel> teilnehmer = new ArrayList<>();

    /**
     * Der Name des Organisators.
     */
    private OrganisatorModel organisator;

    /**
     * Der Name der Veranstaltung.
     */
    private String veranstaltungName = "";
    
    /**
     * Erstellungsdatum der Terminfindung
     */    
    private Date createDate;
    
    /**
     * Letztes Bearbeitungsdatum der Terminfinung (Schließt die Bearbeitung der Teilnehmerliste oder 
     * des Mappings der Teilnehmer zu Zeiträumen nicht mit ein)
     */    
    private Date updateDate;

    /**
     * Sucht in einer TerminfindungModel nach einem Zeitraum mit der angegebenen Id.
     *
     * @param zeitraumId Die gesuchte Id
     * @return Der Zeitraum, wenn er in der Terminfindung vorhanden ist, sonst null.
     */
    public ZeitraumModel findeZeitraumById(long zeitraumId) {

        ZeitraumModel result = null;
        if (tage == null) return null;

        for (TagModel t : tage) {
            if (t.getZeitraeume() != null) {
                for (ZeitraumModel z : t.getZeitraeume()) {
                    if (z.getId() == zeitraumId) result = z;
                }
            }
        }
        return result;
    }

    /**
     * Ermittelt, ob in einer Terminfindung ein bestimmter Teilnehmername bereits vergeben ist.
     *
     * @param name der gesuchte Name
     * @return true, wenn der Namen bereits für einen Teilnehmer vergeben ist, false wenn nicht.
     */
    public boolean existsTeilnehmerName(String name) {

        if (teilnehmer == null) return false;

        for (TeilnehmerModel tn : teilnehmer) {
            if (tn.getName().equals(name)) return true;
        }
        return false;
    }


    /**
     * Liefert einen Liste aller Zeitraeume der Terminfindung ueber alle Tage. Die Liste ist in der Reihenfolge der Tage
     * sortiert.
     *
     * @return Liste all Zeitraeume sortiert nach Tagen
     */
    public List<ZeitraumModel> getAlleZeitraeume() {
        if (tage == null) return Collections.emptyList();

        List<ZeitraumModel> zeitraeume = new ArrayList<>();
        for (TagModel t : tage) {
            if (t.getZeitraeume() != null) {
                zeitraeume.addAll(t.getZeitraeume());
            }
        }
        return zeitraeume;

    }

    /**
     * Gibt zurück ob eine Terminfindung abgeschlossen ist. Eine Terminfindung ist genau dann abgeschlossen, wenn ein
     * definitiver Zeitraum eingetragen ist.
     *
     * @return true, wenn abgeschlossen, sonst false
     */
    public boolean isAbgeschlossen() {

        return defZeitraum != null;
    }

    public boolean isFestgelegterZeitraum(ZeitraumModel zeitraumModel) {
        return defZeitraum != null && Objects.equals(zeitraumModel.getId(), defZeitraum.getId());
    }


	/* Getter und Setter */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ZeitraumModel getDefZeitraum() {
        return defZeitraum;
    }

    public void setDefZeitraum(ZeitraumModel defZeitraum) {
        this.defZeitraum = defZeitraum;
    }

    public List<TagModel> getTage() {
        return tage;
    }

    public void setTage(List<TagModel> tage) {
        this.tage = tage;
    }

    public List<TeilnehmerModel> getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(List<TeilnehmerModel> teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public OrganisatorModel getOrganisator() {
        return organisator;
    }

    public void setOrganisator(OrganisatorModel organisator) {
        this.organisator = organisator;
    }

    public String getVeranstaltungName() {
        return veranstaltungName;
    }

    public void setVeranstaltungName(String veranstaltungName) {
        this.veranstaltungName = veranstaltungName;
    }

    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getTeilnehmerLabel() {
        List<String> teilnehmerNamen = new ArrayList<>();
        if (teilnehmer != null) {
            for (TeilnehmerModel t : teilnehmer) {
                teilnehmerNamen.add(t.getName());
            }
        }
        return StringUtils.join(teilnehmerNamen, ", ");
    }

    public String getTeilnehmerLabel(ZeitraumModel zeitraumModel, Integer pref) {
        List<String> teilnehmerNamen = new ArrayList<>();
        for (TeilnehmerZeitraumModel teilnehmerZeitraumModel : zeitraumModel.getTeilnehmerZeitraeume()) {
            if (teilnehmerZeitraumModel.getPraeferenz().getPrefNumber() == pref) {
                teilnehmerNamen.add(teilnehmerZeitraumModel.getTeilnehmer().getName());
            }
        }
        return StringUtils.join(teilnehmerNamen, ", ");
    }
}
