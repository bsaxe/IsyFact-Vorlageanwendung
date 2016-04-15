package de.msg.terminfindung.gui.terminfindung;

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


import de.msg.terminfindung.gui.terminfindung.model.TerminfindungModel;

import java.io.Serializable;

/**
 * Basisklasse für alle View-Models. Die Klasse bündelt Modell-Elemente, die in mehreren Views verwendet werden.
 *
 * @author Dirk Jäger, msg systems ag
 */
public abstract class AbstractModel implements Serializable {
    private static final long serialVersionUID = -2479477611892278312L;

    private TerminfindungModel terminfindung;

    private boolean testMode = false;

    public TerminfindungModel getTerminfindung() {
        return terminfindung;
    }

    public void setTerminfindung(TerminfindungModel terminfindung) {
        this.terminfindung = terminfindung;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
}
