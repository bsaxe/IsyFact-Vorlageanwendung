package de.msg.terminfindung.gui.testdaten;

import de.msg.terminfindung.common.IdGenerator;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;

import java.util.Date;

/**
 * Erstellt Testdaten für GUI-Tests, d.h. Tests des AWK-Wrappers und der Controller.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public class GuiTestdaten {

    private static final IdGenerator TAG_IDS = new IdGenerator(300L);

    private static final IdGenerator ZEITRAUM_IDS = new IdGenerator(400L);

    public static TagModel erstelleTermin() {
        TagModel tagModel = new TagModel(TAG_IDS.nextId());
        tagModel.setDatum(new Date());

        ZeitraumModel zeitraumModel1 = new ZeitraumModel(ZEITRAUM_IDS.nextId(), "morgens");
        ZeitraumModel zeitraumModel2 = new ZeitraumModel(ZEITRAUM_IDS.nextId(), "abends");

        tagModel.getZeitraeume().add(zeitraumModel1);
        tagModel.getZeitraeume().add(zeitraumModel2);

        return tagModel;
    }

    private GuiTestdaten() {
    }
}
