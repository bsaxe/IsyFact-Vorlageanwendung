package de.msg.terminfindung.persistence.entity;

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


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Entity implementation class for Entity: Terminfindung Haupt-Entitaet, von hier aus kann zu den abhängigen Entitäten
 * navigiert werden.
 *
 * @author msg systems ag, Maximilian Falter
 */
@Entity
public class Terminfindung extends AbstraktEntitaet {
    private static final long serialVersionUID = 1L;

    private String veranstaltungName;

    @Embedded
    private Organisator organisator;

    @OneToOne
    @JoinColumn(name = "zeitraum_nr")
    private Zeitraum defZeitraum;

    /**
     * Liste der zur Terminfindung gehörenden Tage.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "terminfindung_id")
    @OrderBy("datum ASC")
    private List<Tag> termine = new ArrayList<>();

    /**
     * Liste der zur Teminfindung gehörender Teilnehmer
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "terminfindung_id")
    @OrderBy("name ASC")
    private List<Teilnehmer> teilnehmer = new ArrayList<>();
    
    /**
     * Erstellungsdatum der Terminfindung
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
    /**
     * Letztes Bearbeitungsdatum der Terminfinung (Schließt die Bearbeitung der Teilnehmerliste oder 
     * des Mappings der Teilnehmer zu Zeiträumen nicht mit ein)
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Terminfindung() {

    }

    public Terminfindung(String veranstaltungName, Organisator organisator) {
        this.veranstaltungName = veranstaltungName;
        this.organisator = organisator;
    }

    public String getVeranstaltungName() {
        return veranstaltungName;
    }

    public void setVeranstaltungName(String veranstName) {
        this.veranstaltungName = veranstName;
    }

    public Zeitraum getDefZeitraum() {
        return defZeitraum;
    }

    public void setDefZeitraum(Zeitraum defZeitraum) {
        this.defZeitraum = defZeitraum;
    }

    public List<Tag> getTermine() {
        return termine;
    }

    public void setTermine(List<Tag> termine) {
        this.termine = termine;
    }

    public List<Teilnehmer> getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(List<Teilnehmer> teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public Organisator getOrganisator() {
        return organisator;
    }

    public void setOrganisator(Organisator organisator) {
        this.organisator = organisator;
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

	/**
     * Sucht in einer Terminfindung nach einem Zeitraum mit der angegebenen Id.
     *
     * @param zeitraumId Die gesuchte Id
     * @return Der Zeitraum, wenn er in der Terminfindung vorhanden ist, sonst null.
     */
    public Zeitraum findeZeitraumById(long zeitraumId) {

        Zeitraum result = null;
        if (termine == null) return null;

        for (Tag t : termine) {
            if (t.getZeitraeume() != null) {
                for (Zeitraum z : t.getZeitraeume()) {
                    if (z.getId() == zeitraumId) result = z;
                }
            }
        }
        return result;
    }
}
