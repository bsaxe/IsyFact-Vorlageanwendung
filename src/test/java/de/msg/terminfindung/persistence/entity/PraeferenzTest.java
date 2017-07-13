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


import org.junit.Test;

/**
 * Testet den Aufzählungstyp {@link Praeferenz} und stellt sicher,
 * dass die Reihenfolge der Elemente nicht versehentlich geändert wird.
 *
 * @author msg systems ag, Dirk Jäger
 */
public class PraeferenzTest {

    /**
     * Absicherung der Reihenfolge der Enum-Werte im Enum Praeferenz.
     * Verhindert, dass die Reihenfolge versehentlich geändert wird und
     * nicht mehr zu den Daten (Ordinal-Werten) in der Datenbank passt.
     */
    @Test
    public void ordinalFixedTest() {
        assert (Praeferenz.NEIN.ordinal() == 0);
        assert (Praeferenz.JA.ordinal() == 1);
        assert (Praeferenz.WENN_ES_SEIN_MUSS.ordinal() == 2);
    }
}
