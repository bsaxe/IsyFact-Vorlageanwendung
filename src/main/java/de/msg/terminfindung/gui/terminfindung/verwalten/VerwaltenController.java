package de.msg.terminfindung.gui.terminfindung.verwalten;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.exception.TerminfindungTechnicalException;
import de.msg.terminfindung.gui.terminfindung.AbstractController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

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

/**
 * Controller fuer den Verwalten Flow
 *
 * @author msg systems ag, Dirk Jäger
 */
@Controller
public class VerwaltenController extends AbstractController<VerwaltenModel> {

    private static final Logger LOG = Logger.getLogger(VerwaltenController.class);

    /**
     * Initialisiert das Model.
     *
     * @param model Das Model
     * @throws TerminfindungBusinessException
     */
    public void initialisiereModel(VerwaltenModel model) throws TerminfindungTechnicalException, TerminfindungBusinessException {

        LOG.info("Initialisiere das Modell");

        super.holeTerminfindung(model);
        aktualisiereModel(model);
    }

    /**
     * Aktualisiert die abhängigen Daten im Model anhand der vorhandenen Terminfindung.
     * Diese Methode ist vom Laden der Terminfindung getrennt, um ggf. bei
     * Änderungen am Terminfindungs-Attribut des Model die davon abhängigen Daten des
     * Models neu berechnen zu können, ohne die Terminfindung wieder neu aus
     * dem Anwendungskern zu laden.
     *
     * @param model Das Model.
     */
    public void aktualisiereModel(VerwaltenModel model) {

        LOG.info("Aktualisiere das Modell.");
    }
}
