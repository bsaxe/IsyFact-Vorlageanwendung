package de.msg.terminfindung.gui.testdaten;

import de.msg.terminfindung.common.IdGenerator;
import de.msg.terminfindung.gui.terminfindung.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Erstellt Testdaten für GUI-Tests, d.h. Tests des AWK-Wrappers und der Controller.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public class GuiTestdaten {

    private static final IdGenerator TAG_IDS = new IdGenerator(300L);

    private static final IdGenerator ZEITRAUM_IDS = new IdGenerator(400L);

    private static final IdGenerator TEILNEHMER_IDS = new IdGenerator(500L);

    private static final IdGenerator TEILNEHMER_ZEITRAUM_IDS = new IdGenerator(600L);

    public static TagModel erstelleTermin() {
        TagModel tagModel = new TagModel(TAG_IDS.nextId());
        tagModel.setDatum(new Date());

        ZeitraumModel zeitraumModel1 = new ZeitraumModel(ZEITRAUM_IDS.nextId(), "morgens");
        ZeitraumModel zeitraumModel2 = new ZeitraumModel(ZEITRAUM_IDS.nextId(), "abends");

        tagModel.getZeitraeume().add(zeitraumModel1);
        tagModel.getZeitraeume().add(zeitraumModel2);

        return tagModel;
    }

    public static List<TeilnehmerModel> erstelleTeilnehmer() {
        List<TeilnehmerModel> teilnehmer = new ArrayList<>();
        teilnehmer.add(new TeilnehmerModel(TEILNEHMER_IDS.nextId(), "Hans"));
        teilnehmer.add(new TeilnehmerModel(TEILNEHMER_IDS.nextId(), "Heinz"));
        teilnehmer.add(new TeilnehmerModel(TEILNEHMER_IDS.nextId(), "Klaus"));
        return teilnehmer;
    }

    public static TerminfindungModel erstelleTerminfindung() {
        TerminfindungModel terminfindungModel = new TerminfindungModel();
        terminfindungModel.setOrganisator(new OrganisatorModel("Heike"));
        terminfindungModel.setVeranstaltungName("Junggesellenabschied");
        terminfindungModel.getTage().add(erstelleTermin());
        terminfindungModel.getTeilnehmer().addAll(erstelleTeilnehmer());

        for (TagModel tag : terminfindungModel.getTage()) {
            for (ZeitraumModel zeitraum : tag.getZeitraeume()) {
                TeilnehmerZeitraumModel teilnehmerZeitraumModel = new TeilnehmerZeitraumModel();
                teilnehmerZeitraumModel.setId(TEILNEHMER_ZEITRAUM_IDS.nextId());
                teilnehmerZeitraumModel.setTeilnehmer(terminfindungModel.getTeilnehmer().get(0));
                teilnehmerZeitraumModel.setPraeferenz(PraeferenzModel.WENN_ES_SEIN_MUSS);
                zeitraum.getTeilnehmerZeitraeume().add(teilnehmerZeitraumModel);

                teilnehmerZeitraumModel = new TeilnehmerZeitraumModel();
                teilnehmerZeitraumModel.setId(TEILNEHMER_ZEITRAUM_IDS.nextId());
                teilnehmerZeitraumModel.setTeilnehmer(terminfindungModel.getTeilnehmer().get(1));
                teilnehmerZeitraumModel.setPraeferenz(PraeferenzModel.JA);
                zeitraum.getTeilnehmerZeitraeume().add(teilnehmerZeitraumModel);

                teilnehmerZeitraumModel = new TeilnehmerZeitraumModel();
                teilnehmerZeitraumModel.setId(TEILNEHMER_ZEITRAUM_IDS.nextId());
                teilnehmerZeitraumModel.setTeilnehmer(terminfindungModel.getTeilnehmer().get(2));
                teilnehmerZeitraumModel.setPraeferenz(PraeferenzModel.NEIN);
                zeitraum.getTeilnehmerZeitraeume().add(teilnehmerZeitraumModel);
            }
        }

        return terminfindungModel;
    }

    private GuiTestdaten() {
    }
}
