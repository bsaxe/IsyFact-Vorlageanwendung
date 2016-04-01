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
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Entity implementation class for Entity: Tag
 *
 * @author msg systems ag, Maximilian Falter
 *
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "tag_nr")
	private long tag_Nr;
	
	@Column(name = "datum")
	private Date datum;
	private static final long serialVersionUID = 1L;
	@OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL},orphanRemoval=true)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name="tag_nr", referencedColumnName="tag_nr")
	@OrderBy("zeitraum_Nr")
	private List<Zeitraum> zeitraeume = new ArrayList<>();
	
	public Tag() {
		super();
	}   
	
	public Tag(Date datum) {
		super();
		this.datum = datum;
	}

	public long getTag_Nr() {
		return this.tag_Nr;
	}

	public void setTag_Nr(long TerminNr) {
		this.tag_Nr = TerminNr;
	}   
	public Date getDatum() {
		return this.datum;
	}

	public void setDatum(Date Datum) {
		this.datum = Datum;
	}
	public List<Zeitraum> getZeitraeume() {
		return zeitraeume;
	}
	public void setZeitraeume(List<Zeitraum> zeitraeume) {
		this.zeitraeume = zeitraeume;
	}
}
