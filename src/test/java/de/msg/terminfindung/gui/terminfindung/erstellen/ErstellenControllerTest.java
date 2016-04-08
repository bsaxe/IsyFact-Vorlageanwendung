package de.msg.terminfindung.gui.terminfindung.erstellen;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.gui.awkwrapper.AwkWrapper;
import de.msg.terminfindung.gui.terminfindung.model.OrganisatorModel;
import de.msg.terminfindung.gui.terminfindung.model.TagModel;
import de.msg.terminfindung.gui.terminfindung.model.TerminfindungModel;
import de.msg.terminfindung.gui.terminfindung.model.ZeitraumModel;
import de.msg.terminfindung.gui.testdaten.GuiTestdaten;
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

    @Test
    public void testLoescheZeitraum() throws Exception {
        ErstellenController controller = new ErstellenController();

        // Vorbereitung der Testdaten
        TagModel termin = GuiTestdaten.erstelleTermin();
        int anzahlZeitraeume = termin.getZeitraeume().size();
        ZeitraumModel geloeschterZeitraum = termin.getZeitraeume().get(0);

        ErstellenModel model = new ErstellenModel();
        model.getTage().add(termin);
        model.setSelectedZeitraum(geloeschterZeitraum);

        // Test
        controller.delZeitraum(model);

        assertNotNull(model);
        assertNotNull(model.getTage());
        assertEquals(anzahlZeitraeume - 1, model.getTage().get(0).getZeitraeume().size());
        assertFalse(model.getTage().get(0).getZeitraeume().contains(geloeschterZeitraum));
    }

    @Test
    public void testValidiereErstellenModel() throws TerminfindungBusinessException {
        // Vorbereitung des Testobjekts
        ErstellenController controller = new ErstellenController();
        AwkWrapper awkWrapper = mock(AwkWrapper.class);
        controller.setAwk(awkWrapper);

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

        // Vorbereitung der Testdaten
        TagModel termin = GuiTestdaten.erstelleTermin();
        int anzahlZeitraeume = termin.getZeitraeume().size();

        ErstellenModel model = new ErstellenModel();
        model.setName("Hans' Geburtstag");
        model.setOrgName("Hans");
        model.getTage().add(termin);

        // Test
        assertTrue(controller.validiereErstellenModel(model));

        verify(awkWrapper).erstelleTerminfindung(anyString(), anyString(), anyListOf(TagModel.class));
        verifyNoMoreInteractions(awkWrapper);

        assertNotNull(model.getTerminfindung());
        assertNotNull(model.getTage());
        assertEquals(anzahlZeitraeume, model.getTage().get(0).getZeitraeume().size());
        for (ZeitraumModel zeitraum : termin.getZeitraeume()) {
            assertTrue(model.getTerminfindung().getTage().get(0).getZeitraeume().contains(zeitraum));
        }
    }

    @Test
    public void testValidiereErstellenModelAusnahme() throws TerminfindungBusinessException {
        // Vorbereitung des Testobjekts
        ErstellenController controller = new ErstellenController();
        AwkWrapper awkWrapper = mock(AwkWrapper.class);
        controller.setAwk(awkWrapper);

        when(awkWrapper.erstelleTerminfindung(anyString(), anyString(), anyListOf(TagModel.class))).thenThrow(TerminfindungBusinessException.class);

        // Vorbereitung der Testdaten
        ErstellenModel model = new ErstellenModel();
        model.setName("Hans' Geburtstag");
        model.setOrgName("Hans");
        model.getTage().add(GuiTestdaten.erstelleTermin());

        // Test
        assertFalse(controller.validiereErstellenModel(model));
    }

}