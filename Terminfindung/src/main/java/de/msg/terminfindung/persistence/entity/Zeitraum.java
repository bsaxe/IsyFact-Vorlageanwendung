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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Entity implementation class for Entity: Zeitraum
 * Stellt einen spezifischen Zeitraum fuer ein Datum (Tag) dar.
 * Der Zeitraum wird rein textuell beschrieben, z.B. "11:00-12:00" oder "abends"
 *
 * @author msg systems ag, Maximilian Falter
 */
@Entity
@Table(name = "zeitraum")
public class Zeitraum implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "zeitraum_nr")
	private long zeitraum_Nr;
	
	@Column(name = "beschreibung")
	private String beschreibung;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = { CascadeType.ALL})
	@JoinColumn(name="zeitraum_nr", referencedColumnName="zeitraum_nr")
	private List<TeilnehmerZeitraum> teilnehmerZeitraeume = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "tag_nr")
	private Tag tag;
	
	public Zeitraum() {
		super();
	}   
	public Zeitraum(String beschreibung) {
		super();
		this.beschreibung = beschreibung;
	}
	public long getZeitraum_Nr() {
		return this.zeitraum_Nr;
	}
	public void setZeitraum_Nr(long zeitraumNr) {
		this.zeitraum_Nr = zeitraumNr;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public List<TeilnehmerZeitraum> getTeilnehmerZeitraeume() {
		return teilnehmerZeitraeume;
	}
	public void setTeilnehmerZeitraeume(List<TeilnehmerZeitraum> teilnehmerZeitraeume) {
		this.teilnehmerZeitraeume = teilnehmerZeitraeume;
	}
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}   
   
}
