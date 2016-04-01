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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Entity implementation class for Entity: Terminfindung
 * Haupt-Entitaet, von hier aus kann zu den abhängigen Entitäten navigiert werden.
 *
 * @author msg systems ag, Maximilian Falter
 *
 */
@Entity
@Table(name = "terminfindung")
public class Terminfindung implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "terminfnd_nr")
	private long terminfnd_Nr;
	
	@OneToOne
	@JoinColumn(name="zeitraum_nr")
	Zeitraum defZeitraum;
	private static final long serialVersionUID = 1L;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="terminfnd_nr", referencedColumnName="terminfnd_nr")
	@OrderBy("datum ASC")
	private List<Tag> termine = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="terminfnd_nr", referencedColumnName="terminfnd_nr")
	@OrderBy("name ASC")
	private List<Teilnehmer> teilnehmer = new ArrayList<>();
	
	@Embedded private Organisator organisator;
	
	@Column( name = "veranst_name")
	private String veranstName = "";
	
	public Terminfindung() {
		super();
	}   
		
	public Terminfindung(Organisator organisator, String veranstName) {
		super();
		this.organisator = organisator;
		this.veranstName = veranstName;
	}

	public long getTerminfnd_Nr() {
		return this.terminfnd_Nr;
	}

	public void setTerminfnd_Nr(long Terminfnd_Nr) {
		this.terminfnd_Nr = Terminfnd_Nr;
	}   

	public String getVeranstName() {
		return veranstName;
	}

	public void setVeranstName(String veranstName) {
		this.veranstName = veranstName;
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
   	
	/**
	 * Sucht in einer Terminfindung nach einem Zeitraum mit der angegebenen Id.
	 * 
	 * @param zeitraumId Die gesuchte Id
	 * @return Der Zeitraum, wenn er in der Terminfindung vorhanden ist, sonst null.
	 */
	public Zeitraum findeZeitraumById (long zeitraumId) {
		
		Zeitraum result = null;
		if (termine == null) return null;
		
		for (Tag t : termine) {
			if (t.getZeitraeume() != null) {
				for (Zeitraum z : t.getZeitraeume()) {
					if (z.getZeitraum_Nr() == zeitraumId) result=z;
				}
			}
		}
		return result;
	}
}
