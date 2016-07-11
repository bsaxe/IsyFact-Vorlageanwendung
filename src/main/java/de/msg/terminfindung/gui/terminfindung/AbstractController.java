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


import de.bund.bva.isyfact.common.web.global.GlobalFlowController;
import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.logging.LogKategorie;
import de.bund.bva.pliscommon.konfiguration.common.ReloadableKonfiguration;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.exception.TerminfindungTechnicalException;
import de.msg.terminfindung.common.konstanten.EreignisSchluessel;
import de.msg.terminfindung.common.konstanten.FehlerSchluessel;
import de.msg.terminfindung.gui.awkwrapper.AwkWrapper;
import de.msg.terminfindung.gui.terminfindung.model.TerminfindungModel;
import de.msg.terminfindung.gui.util.TFNumberHolder;


/**
 * Basisklasse aller Controller.
 * Die Klasse hält die Referenz zum Anwendungskern.
 *
 * @param <T> Die Klasse des Models
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
public abstract class AbstractController<T extends AbstractModel> {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(AbstractController.class);

    protected AwkWrapper awk;

    protected TFNumberHolder tfNumberHolder;

    protected GlobalFlowController globalFlowController;

    protected ReloadableKonfiguration konfiguration;

    protected void initialisiereModell(AbstractModel model) {

        model.setTestMode(this.isTestMode());
    }
    
    /**
     * Holt die Terminfindung aus dem Anwendungkern.
     * Die Nummer der Terminfindung wird anhand des übergebenen Request
     * Parameters bestimmt.
     *
     * @param model Das Model
     * @throws TerminfindungBusinessException
     */
    protected void holeTerminfindung(T model) throws TerminfindungTechnicalException, TerminfindungBusinessException {

        if (tfNumberHolder.getNumber() == null) {
            throw new TerminfindungTechnicalException(FehlerSchluessel.MSG_KEINE_TERMINFINDUNGSNR);
        }

        LOG.infoFachdaten(LogKategorie.JOURNAL, EreignisSchluessel.MSG_TERMINFINDUNG_GET, "Hole Terminfindung vom Anwendungskern für Terminfindungsnummer {}", tfNumberHolder.getNumber());

        TerminfindungModel terminfindung = awk.ladeTerminfindung(tfNumberHolder.getNumber());
        model.setTerminfindung(terminfindung);
    }

    public boolean isTestMode() {
        return konfiguration.getAsBoolean("terminfindung.test.mode");
    }

    public AwkWrapper getAwk() {
        return awk;
    }

    public void setAwk(AwkWrapper awk) {
        this.awk = awk;
    }

    public TFNumberHolder getTfNumberHolder() {
        return tfNumberHolder;
    }

    public void setTfNumberHolder(TFNumberHolder tfNumberHolder) {
        this.tfNumberHolder = tfNumberHolder;
    }

    public GlobalFlowController getGlobalFlowController() {
        return globalFlowController;
    }

    public void setGlobalFlowController(
            GlobalFlowController globalFlowController) {
        this.globalFlowController = globalFlowController;
    }

    public ReloadableKonfiguration getKonfiguration() {
        return konfiguration;
    }

    public void setKonfiguration(ReloadableKonfiguration konfiguration) {
        this.konfiguration = konfiguration;
    }
}
