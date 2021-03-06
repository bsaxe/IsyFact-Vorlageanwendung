package de.msg.terminfindung.gui.util;

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


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import de.msg.terminfindung.gui.terminfindung.model.TagModel;

/**
 * @author msg systems ag, Maximilian Falter
 * Comparator zum Sortieren einer Liste von StartTerminen 
 * (Klasse zum Abbilden der Logik des StartModels)
 */
@Deprecated
public class MyDateComparator implements Comparator<TagModel> {
    protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");


	@Override
	public int compare(TagModel o1, TagModel o2) {
		return DATE_FORMAT.format(o1.getDatum()).compareTo(DATE_FORMAT.format(o2.getDatum()));
	}
}
