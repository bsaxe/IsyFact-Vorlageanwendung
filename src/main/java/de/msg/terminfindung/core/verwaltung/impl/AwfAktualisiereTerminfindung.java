package de.msg.terminfindung.core.verwaltung.impl;

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

import de.bund.bva.isyfact.datetime.util.DateTimeUtil;
import de.msg.terminfindung.persistence.dao.TerminfindungDao;
import de.msg.terminfindung.persistence.entity.Terminfindung;

/**
 * Die Klasse implementiert den Anwendungsfall Aktualisiere Stammdaten einer Terminfindung
 * @author vadim
 *
 */
public class AwfAktualisiereTerminfindung {

	private final TerminfindungDao terminfindungDao;
	
	AwfAktualisiereTerminfindung(TerminfindungDao terminfindungDao) {
        this.terminfindungDao = terminfindungDao;
    }
	
	public void aktualisiereTerminfindung(Terminfindung terminfindung, String organisatorName, String veranstaltungName){
		terminfindung.setVeranstaltungName(veranstaltungName);
		terminfindung.getOrganisator().setName(organisatorName);
		terminfindung.setUpdateDate(DateTimeUtil.localDateTimeNow());
		terminfindungDao.aktualisiere(terminfindung);
	}
}
