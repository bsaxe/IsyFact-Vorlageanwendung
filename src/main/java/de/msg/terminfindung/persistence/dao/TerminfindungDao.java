package de.msg.terminfindung.persistence.dao;

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
import java.util.List;

import de.msg.terminfindung.persistence.entity.Terminfindung;

/**
 * DAO fuer den Zugriff auf die Entity Terminfindung
 *
 * @author msg systems ag, Maximilian Falter
 */
public interface TerminfindungDao extends AbstraktDao<Terminfindung> {

    /**
     * Sucht alle Terminfindungen, die vor dem angegebenen Datum stattgefunden haben.
     *
     * @param datum Stichtag
     * @return eine Liste mit den gefundenen Terminfindungen.
     */
    List<Terminfindung> sucheVor(LocalDate datum);

    /**
     * Gibt alle Terminfindungen zurück.
     * 
     * @return Alle Terminfindungen.
     */
	List<Terminfindung> findeAlle();
	
	/**
	 * Sucht nach Terminfindung per UUID.
	 * 
	 * 
	 * @param ref String, der UUID enthält.
	 * @return Wenn vorhanden, Terminfindung mit der Referenz, sonst null. 
	 */
	Terminfindung sucheMitReferenz(String uuid);
	
}
