package de.msg.terminfindung.gui.terminfindung.model;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse speichert einen Zeitraum in der View-Schicht.
 *
 * @author msg systems ag, Maximilian Falter, Dirk Jäger
 */
public class ZeitraumModel implements Comparable<ZeitraumModel>, Serializable {

    private static final long serialVersionUID = -457050384959229232L;

    /**
     * Nummer (Id) des Zeitraums. Die Id des Zeitraums ist eindeutig über alle Tage der Terminfindung hinweg. Sie
     * identifiziert einen Zeitraum damit eindeutig ohne Angabe des Tages, an dem der Zeitraum liegt.
     */
    private Long id;
    /**
     * Beschreibung des Zeitraums.
     */
    private String beschreibung;
    /**
     * Der Tag, an dem der Zeitraum liegt.
     */
    private TagModel tag;
    /**
     * Eine Liste von Präferenzen für diesen Zeitraum aus Sicht des Zeitraums. Wenn ein Teilnehmer eine Präferenz für
     * diesen Zeitraum angibt, wird in dieser Liste ein Objekt der Klasse {@link TeilnehmerZeitraumModel} hinzugefügt,
     * das die Verbindung zwischen dem Teilnehmer und dem Zeitraum herstellt. "Präferenz" für einen Zeitraum bedeutet
     * nicht unbedingt, dass der Teilnehmer sich FÜR diesen Zeitraum entscheidet, d.h. mit "Ja" abstimmt. Jeder
     * Teilnehmer trägt immer für jeden Zeitraum eine Präferenz ein wenn er abstimmt. In diesem Sinne sind auch "Nein"
     * und "Wenn es sein muss" Präferenzen.
     */
    private List<TeilnehmerZeitraumModel> teilnehmerZeitraeume = new ArrayList<>();

    /**
     * Anzahl an Zusagen über alle Teilnehmer. Wird erst berechnet, wenn es zum ersten Mal von einer View benötigt
     * wird.
     */
    private Integer zusagen;

    /**
     * Anzahl an Unsicheren über alle Teilnehmer. Wird erst berechnet, wenn es zum ersten Mal von einer View benötigt
     * wird.
     */
    private Integer unsicher;

    /**
     * Auswertung der Präferenzen durch Zählen der abgegebenen Stimmen für den Zeitraum. Die Methode durchläuft dazu die
     * Liste der Präferenzen und zählt, wie viele davon "Ja", "Nein" oder "Wenn es sein muss" sind.
     *
     * @return Ein Integer Array mit drei Elementen, die die "Ja", "Nein" und "Wenn es sein muss" Stimmen angeben.
     */
    public Integer[] zaehleStimmen() {

        Integer[] count = {0, 0, 0};

        for (TeilnehmerZeitraumModel tz : teilnehmerZeitraeume) {
            if (tz.getPraeferenz() == PraeferenzModel.NEIN) count[0]++;
            else if (tz.getPraeferenz() == PraeferenzModel.JA) count[1]++;
            else if (tz.getPraeferenz() == PraeferenzModel.WENN_ES_SEIN_MUSS) count[2]++;
        }
        return count;
    }

    public int getZusagen() {
        if (zusagen == null) {
            zusagen = 0;
            for (TeilnehmerZeitraumModel tz : teilnehmerZeitraeume) {
                if (tz.getPraeferenz() == PraeferenzModel.JA) {
                    zusagen++;
                }
            }
        }
        return zusagen;
    }

    public int getUnsicher() {
        if (unsicher == null) {
            unsicher = 0;
            for (TeilnehmerZeitraumModel tz : teilnehmerZeitraeume) {
                if (tz.getPraeferenz() == PraeferenzModel.WENN_ES_SEIN_MUSS) {
                    unsicher++;
                }
            }
        }
        return unsicher;
    }

    public PraeferenzModel getPraeferenzFuerTeilnehmer(String name) {
        for (TeilnehmerZeitraumModel teilnehmerZeitraum : teilnehmerZeitraeume) {
            if (teilnehmerZeitraum.getTeilnehmer().getName().equals(name)) {
                return teilnehmerZeitraum.getPraeferenz();
            }
        }
        return PraeferenzModel.NEIN;
    }

    public List<TeilnehmerZeitraumModel> getTeilnehmerZeitraeume() {
        return teilnehmerZeitraeume;
    }

    public void setTeilnehmerZeitraeume(
            List<TeilnehmerZeitraumModel> teilnehmerZeitraeume) {
        this.teilnehmerZeitraeume = teilnehmerZeitraeume;
    }

    public ZeitraumModel(long id, String beschreibung) {
        super();
        this.id = id;
        this.beschreibung = beschreibung;
    }

    public ZeitraumModel() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public TagModel getTag() {
        return tag;
    }

    public void setTag(TagModel tag) {
        this.tag = tag;
    }

    @Override
    public int compareTo(ZeitraumModel o) {
        return getBeschreibung().compareTo(o.getBeschreibung());
    }

}
