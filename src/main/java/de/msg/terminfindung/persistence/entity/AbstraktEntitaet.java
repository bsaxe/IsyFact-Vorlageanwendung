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
import java.io.Serializable;

/**
 * Basisklasse aller Entitäten der Anwendung. Alle Entitäten enthalten einen Primärschlüssel des gleichen Typs und mit
 * der gleichen Erzeugungsstrategie. Je nach Anwendung können hier auch weitere Felder aufgenommen werden, z.B. zum
 * Auditing.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstraktEntitaet implements Serializable {
	private static final long serialVersionUID = 1197581822091195412L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
