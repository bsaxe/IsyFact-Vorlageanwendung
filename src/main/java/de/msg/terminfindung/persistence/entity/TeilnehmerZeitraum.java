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

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

/**
 * Stellt die Entität für die Präferenz eines Teilnehmers dar. Wird benötigt, da jeder Teilnehmer zu mehreren
 * Zeiträumen eine Präferenz hat und jeder Zeitraum von mehreren Teilnehmern präferiert werden kann.
 *
 * @author msg systems ag, Stefan Dellmuth
 */
@Entity
public class TeilnehmerZeitraum extends AbstraktEntitaet {
    private static final long serialVersionUID = 1L;

    @Enumerated(value = EnumType.ORDINAL)
    private Praeferenz praeferenz;

    @OneToOne
    private Teilnehmer teilnehmer;

    @OneToOne
    private Zeitraum zeitraum;

    public TeilnehmerZeitraum() {

    }

    public TeilnehmerZeitraum(Teilnehmer teilnehmer, Zeitraum zeitraum, Praeferenz praeferenz) {
        this.teilnehmer = teilnehmer;
        this.zeitraum = zeitraum;
        this.praeferenz = praeferenz;
    }

    public Praeferenz getPraeferenz() {
        return this.praeferenz;
    }

    public void setPraeferenz(Praeferenz praeferenz) {
        this.praeferenz = praeferenz;
    }

    public Teilnehmer getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(Teilnehmer teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public Zeitraum getZeitraum() {
        return zeitraum;
    }

    public void setZeitraum(Zeitraum zeitraum) {
        this.zeitraum = zeitraum;
    }
}
