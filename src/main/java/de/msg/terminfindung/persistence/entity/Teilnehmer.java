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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Teilnehmer
 *
 * @author msg systems ag, Maximilian Falter
 */
@Entity
@Table(name = "teilnehmer")
public class Teilnehmer implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "teilnehmer_nr")
	private long teilnehmer_Nr;
	
	
	@Column(name = "name")
	private String name = "";
	private static final long serialVersionUID = 1L;
		
	public Teilnehmer() {
		super();
			}   
		
	public Teilnehmer(String name) {
		super();
		this.name = name;
	}

	public long getTeilnehmer_Nr() {
		return this.teilnehmer_Nr;
	}

	public void setTeilnehmer_Nr(long TeilnehmerNr) {
		this.teilnehmer_Nr = TeilnehmerNr;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

}
