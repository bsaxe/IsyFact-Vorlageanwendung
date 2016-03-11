package de.msg.terminfindung.gui.terminfindung.teilnehmen;

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

import de.msg.terminfindung.gui.komponenten.ZeitraumAuswahlModelComponent;
import de.msg.terminfindung.gui.terminfindung.AbstractModel;

/**
 * Model fuer den Teilnehmen Flow
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
public class TeilnehmenModel extends AbstractModel implements Serializable {

	private static final long serialVersionUID = 8610872504330422543L;

	/** Speichert den Namen des Teilnehmers*/
	private String teilnehmerName;
	/** Speichert die Präferenzen des Teilnehmers für die einzelnen Zeiträume,
	 * die zur Auswahl stehen. */
	private ZeitraumAuswahlModelComponent zeitraumAuswahl = new ZeitraumAuswahlModelComponent();

	public String getTeilnehmerName() {
		return teilnehmerName;
	}

	public void setTeilnehmerName(String teilnehmerName) {
		this.teilnehmerName = teilnehmerName;
	}

	public ZeitraumAuswahlModelComponent getZeitraumAuswahl() {
		return zeitraumAuswahl;
	}

	public void setZeitraumAuswahl(ZeitraumAuswahlModelComponent zeitraumAuswahl) {
		this.zeitraumAuswahl = zeitraumAuswahl;
	}
}


