package de.msg.terminfindung.gui.terminfindung.erstellen;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.gui.awkwrapper.AwkWrapper;
import de.msg.terminfindung.gui.terminfindung.model.OrganisatorModel;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.TerminfindungModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Beispielhafte Testklasse für einen Controller. Hier werden die Interaktionen zwischen View und Model, die Validierung
 * des Models und die korrekte Weitergabe der Daten an den AWK-Wrapper getestet. Dazu wird ein Mock des AWK-Wrappers
 * erstellt.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public class ErstellenControllerTest {

    private AwkWrapper awkWrapper;

    @Before
    public void init() {
        awkWrapper = mock(AwkWrapper.class);
    }

    @Test
    public void testLoescheZeitraum() throws Exception {
        ErstellenController controller = new ErstellenController();

        TagModel tagModel = new TagModel();
        ZeitraumModel zeitraumModel1 = new ZeitraumModel(1L, "morgens");
        ZeitraumModel zeitraumModel2 = new ZeitraumModel(2L, "abends");

        tagModel.getZeitraeume().add(zeitraumModel1);
        tagModel.getZeitraeume().add(zeitraumModel2);

        // Vorbereitung des View-Models
        ErstellenModel model = new ErstellenModel();
        model.getTage().add(tagModel);
        model.setSelectedZeitraum(zeitraumModel1);

        controller.delZeitraum(model);

        assertNotNull(model);
        assertNotNull(model.getTage());
        assertTrue(model.getTage().get(0).getZeitraeume().contains(zeitraumModel2));
        assertFalse(model.getTage().get(0).getZeitraeume().contains(zeitraumModel1));
    }

    @Test
    public void testValidiereErstellenModel() throws TerminfindungBusinessException {
        when(awkWrapper.erstelleTerminfindung(anyString(), anyString(), anyListOf(TagModel.class))).thenAnswer(new Answer<TerminfindungModel>() {
            @Override
            public TerminfindungModel answer(InvocationOnMock invocation) throws Throwable {
                String organisatorName = invocation.getArgumentAt(0, String.class);
                String veranstaltungName = invocation.getArgumentAt(1, String.class);
                List<TagModel> termine = invocation.getArgumentAt(2, List.class);

                TerminfindungModel terminfindung = new TerminfindungModel();
                terminfindung.setOrganisator(new OrganisatorModel(organisatorName));
                terminfindung.setVeranstaltungName(veranstaltungName);
                terminfindung.setTage(termine);
                return terminfindung;
            }
        });

        ErstellenController controller = new ErstellenController();
        controller.setAwk(awkWrapper);

        TagModel tagModel = new TagModel();
        ZeitraumModel zeitraumModel1 = new ZeitraumModel(1L, "morgens");
        ZeitraumModel zeitraumModel2 = new ZeitraumModel(2L, "abends");

        tagModel.getZeitraeume().add(zeitraumModel1);
        tagModel.getZeitraeume().add(zeitraumModel2);

        // Vorbereitung des View-Models
        ErstellenModel model = new ErstellenModel();
        model.setName("Hans' Geburtstag");
        model.setOrgName("Hans");
        model.getTage().add(tagModel);

        assertTrue(controller.validiereErstellenModel(model));
        verify(awkWrapper).erstelleTerminfindung(anyString(), anyString(), anyListOf(TagModel.class));
        verifyNoMoreInteractions(awkWrapper);

        assertNotNull(model.getTerminfindung());
        assertNotNull(model.getTage());
        assertTrue(model.getTerminfindung().getTage().get(0).getZeitraeume().contains(zeitraumModel2));
        assertTrue(model.getTerminfindung().getTage().get(0).getZeitraeume().contains(zeitraumModel1));
    }

    @Test
    public void testValidiereErstellenModelAusnahme() throws TerminfindungBusinessException {
        when(awkWrapper.erstelleTerminfindung(anyString(), anyString(), anyListOf(TagModel.class))).thenThrow(TerminfindungBusinessException.class);

        ErstellenController controller = new ErstellenController();
        controller.setAwk(awkWrapper);

        TagModel tagModel = new TagModel();
        ZeitraumModel zeitraumModel1 = new ZeitraumModel(1L, "morgens");
        ZeitraumModel zeitraumModel2 = new ZeitraumModel(2L, "abends");

        tagModel.getZeitraeume().add(zeitraumModel1);
        tagModel.getZeitraeume().add(zeitraumModel2);

        // Vorbereitung des View-Models
        ErstellenModel model = new ErstellenModel();
        model.setName("Hans' Geburtstag");
        model.setOrgName("Hans");
        model.getTage().add(tagModel);

        assertFalse(controller.validiereErstellenModel(model));
    }

}