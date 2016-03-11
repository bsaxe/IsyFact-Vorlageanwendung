package de.msg.terminfindung.core.erstellung;

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


import java.util.List;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import de.msg.terminfindung.persistence.entity.Zeitraum;

/**
 * Interface der Anwendungskomponente "Erstellung" zur Erstellung von Terminfindungen
 * TODO: Aufteilen in "Erstellung" und "Verwaltung"
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
public interface Erstellung {

	Terminfindung 	leseTerminfindung(Long terminfindung_nr);
	void 			loescheZeitraeume(Terminfindung terminfindung, List<Zeitraum> zeitraumList);
	Terminfindung 	erstelleTerminfindung(String orgName,String veranstName,List<Tag> tagList) throws TerminfindungBusinessException;
	void 			setzeVeranstaltungstermin(Terminfindung terminfindung, long zeitraumNr) throws TerminfindungBusinessException;

}
