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


import de.msg.terminfindung.gui.terminfindung.model.RequestData;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.TerminfindungModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Basisklasse für alle View-Models. Die Klasse bündelt Modell-Elemente, die in mehreren Views verwendet werden.
 *
 * @author Dirk Jäger, msg systems ag
 */
public abstract class AbstractModel implements Serializable {
    private static final long serialVersionUID = -2479477611892278312L;

    private TerminfindungModel terminfindung;
    private List<TagModel> tage = new ArrayList<>();
    private RequestData requestData;
    private boolean testMode = false;

    public RequestData getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    public boolean zeitraumIsDefZeitraum(ZeitraumModel zeitraumModel) {
        if (terminfindung.getDefZeitraum() != null) {
            return zeitraumModel.getId() == getTerminfindung().getDefZeitraum().getId();
        }
        return false;
    }

    public void setTage(List<TagModel> termine) {
        this.tage = termine;
    }

    public List<TagModel> getTage() {
        return tage;
    }

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
