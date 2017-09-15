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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;


/**
 * Entity implementation class for Entity: Tag
 *
 * @author msg systems ag, Maximilian Falter
 */
@Entity
public class Tag extends AbstraktEntitaet {
    private static final long serialVersionUID = 1L;

    private LocalDate datum;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tag_id")
    @OrderBy("id ASC")
    private List<Zeitraum> zeitraeume = new ArrayList<>();

    public Tag() {
    }

    public Tag(LocalDate datum) {
        this.datum = datum;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public void setDatum(LocalDate Datum) {
        this.datum = Datum;
    }

    public List<Zeitraum> getZeitraeume() {
        return zeitraeume;
    }

    public void setZeitraeume(List<Zeitraum> zeitraeume) {
        this.zeitraeume = zeitraeume;
    }
}
