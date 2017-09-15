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

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import de.bund.bva.isyfact.datetime.zeitraum.persistence.ZeitraumEntitaet;

/**
 * Entity implementation class for Entity: Zeitraum Stellt einen spezifischen Zeitraum fuer ein Datum (Tag) dar. Der
 * Zeitraum wird rein textuell beschrieben, z.B. "11:00-12:00" oder "abends"
 *
 * @author msg systems ag, Maximilian Falter
 */
@Entity
public class Zeitraum extends AbstraktEntitaet {
    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ZeitraumEntitaet zeitraum;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "zeitraum_id")
    private Set<TeilnehmerZeitraum> teilnehmerZeitraeume = new HashSet<>();

    public Zeitraum() {
    }

    public Zeitraum(ZeitraumEntitaet zeitraum) {
        this.zeitraum = zeitraum;
    }

    public ZeitraumEntitaet getZeitraum() {
        return zeitraum;
    }

    public void setZeitraum(ZeitraumEntitaet zeitraum) {
        this.zeitraum = zeitraum;
    }

    public Set<TeilnehmerZeitraum> getTeilnehmerZeitraeume() {
        return teilnehmerZeitraeume;
    }

    public void setTeilnehmerZeitraeume(Set<TeilnehmerZeitraum> teilnehmerZeitraeume) {
        this.teilnehmerZeitraeume = teilnehmerZeitraeume;
    }

}
