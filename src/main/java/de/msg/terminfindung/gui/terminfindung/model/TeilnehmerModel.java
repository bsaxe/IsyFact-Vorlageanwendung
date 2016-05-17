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

/**
 * Die Klasse speichert die Daten eines Teilnehmers in der View-Schicht. Ein Teilnehmer hat eine eindeutige Id, einen
 * Namen und eine Liste von präferierten Zeiträumen (Präferenzen) bzgl. der zur Auswahl stehenden Termine.
 *
 * @author msg systems ag, Dirk Jäger
 */

public class TeilnehmerModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    public TeilnehmerModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TeilnehmerModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
