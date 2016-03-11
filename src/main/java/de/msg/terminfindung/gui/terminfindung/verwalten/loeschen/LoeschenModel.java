package de.msg.terminfindung.gui.terminfindung.verwalten.loeschen;

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
import java.util.HashMap;
import java.util.Map;

import de.msg.terminfindung.gui.terminfindung.AbstractModel;
import org.apache.commons.collections.map.HashedMap;

/**
 * Model fuer den Löschen Flow
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
public class LoeschenModel extends AbstractModel implements Serializable {

	private static final long serialVersionUID = 8610872504330422543L;

	/** Speichert, ob in der GUI ein Zeitraum durch den Benutzer zum Löschen ausgewählt wurde.*/
	private Map<Long,Boolean> checkedByUser = new HashMap<>();

	public Map<Long,Boolean> getCheckedByUser() {
		return checkedByUser;
	}

	public void setCheckedByUser(Map<Long,Boolean> checkedByUser) {
		this.checkedByUser = checkedByUser;
	}
}
