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

public class ViewTeilnehmerZeitraum implements Serializable {

	private long teilnehmerZeitraum_Nr;

	private ViewPraeferenz praeferenz;
	
	private static final long serialVersionUID = 1L;

	ViewTeilnehmer teilnehmer;
	
	ViewZeitraum zeitraum;

	public ViewTeilnehmerZeitraum() {
		super();
	}

	public ViewTeilnehmerZeitraum(ViewTeilnehmer teilnehmer, ViewPraeferenz praeferenz) {
		this.teilnehmer = teilnehmer;
		this.praeferenz = praeferenz;
	}

	public ViewPraeferenz getPraeferenz() {
		return this.praeferenz;
	}

	public void setPraeferenz(ViewPraeferenz praeferenz) {
		this.praeferenz = praeferenz;
	}

	public long getTeilnehmerZeitraum_Nr() {
		return teilnehmerZeitraum_Nr;
	}

	public void setTeilnehmerZeitraum_Nr(long teilnehmerZeitraum_Nr) {
		this.teilnehmerZeitraum_Nr = teilnehmerZeitraum_Nr;
	}

	public ViewTeilnehmer getTeilnehmer() {
		return teilnehmer;
	}

	public void setTeilnehmer(ViewTeilnehmer teilnehmer) {
		this.teilnehmer = teilnehmer;
	}

	public ViewZeitraum getZeitraum() {
		return zeitraum;
	}

	public void setZeitraum(ViewZeitraum zeitraum) {
		this.zeitraum = zeitraum;
	}
}
