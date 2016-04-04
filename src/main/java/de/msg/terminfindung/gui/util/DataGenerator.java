package de.msg.terminfindung.gui.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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


import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;

/**
 * Generierung von Testdaten für manuelle Tests.
 *
 * @author msg systems ag, Dirk Jäger
 */
public class DataGenerator {

    /**
     * Erzeugte eine Dummy-Liste von Tagen mit Zeiträumen.
     * Zum Befüllen des Modells für Testzwecke gedacht.
     *
     * @return Eine fest vorgegebene Test-Liste mit Terminen
     */
    public static List<TagModel> generateTage()  {
    	DecimalFormat format = new DecimalFormat("00");		
        // Erzeuge eine Liste von drei Tagen
       List<TagModel> tage = new ArrayList<>();
        for (int i=0; i<=2; i++) {

            TagModel tag = new TagModel();
            // Tage beginnem vom aktuellen Datum an
            tag.setDatum(DateUtil.getNDaysFromToday(i));
            tage.add(tag);

            // Erzeuge drei Zeiträume für jeden Tage
            List<ZeitraumModel> zeitraeume = new ArrayList<>();
            for (int j=0; j<=2; j++) {

                ZeitraumModel zeitraum = new ZeitraumModel();
                zeitraum.setBeschreibung( format.format(j+8) + ":00 - " + format.format(j+9) + ":00");
                zeitraeume.add(zeitraum);
            }
            tag.setZeitraeume(zeitraeume);
        }
        return tage;
    }
    
    public static List<String> getUhrzeitAuswahl(){
    	List<String> uhrzeitAuswahl = new ArrayList<>();    
    	DecimalFormat format = new DecimalFormat("00");
    	for (int h = 0; h < 24; h++) {
			for (int m = 0; m < 60; m=m+15) {								
				uhrzeitAuswahl.add(format.format(h)+":" + format.format(m));				
			}
		}
    	return uhrzeitAuswahl;
    }
}
