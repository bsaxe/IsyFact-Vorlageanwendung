package de.msg.terminfindung.gui.terminfindung.erstellen;

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
import de.bund.bva.isyfact.common.web.validation.ValidationController;
import de.bund.bva.isyfact.common.web.validation.ValidationMessage;
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

    private static final String ORGANISATOR_NAME = "Hans";

    private static final String VERANSTALTUNG_NAME = "Hans' Geburtstag";

    private static void initialisiereAwkWrapper(AwkWrapper awkWrapper) throws TerminfindungBusinessException {
        when(awkWrapper.erstelleTerminfindung(anyString(), anyString(), anyListOf(TagModel.class))).thenAnswer(new Answer<TerminfindungModel>() {
            @Override
            public TerminfindungModel answer(InvocationOnMock invocation) throws Throwable {
                String organisatorName = invocation.getArgumentAt(0, String.class);
                String veranstaltungName = invocation.getArgumentAt(1, String.class);
                List<TagModel> termine = invocation.getArgumentAt(2, List.class);

                TerminfindungModel terminfindung = new TerminfindungModel();
                terminfindung.setOrganisator(new OrganisatorModel(organisatorName));
                terminfindung.setVeranstaltungName(veranstaltungName);
                terminfindung.getTage().addAll(termine);
                return terminfindung;
            }
        });
    }

    @Test
    public void testLoescheZeitraum() throws Exception {
        // Vorbereitung des Testobjekts
        ErstellenController controller = new ErstellenController();

        // Vorbereitung der Testdaten
        TagModel termin = GuiTestdaten.erstelleTermin();
        int anzahlZeitraeume = termin.getZeitraeume().size();
        ZeitraumModel geloeschterZeitraum = termin.getZeitraeume().get(0);

        ErstellenModel model = new ErstellenModel();
        model.getTage().add(termin);
        model.setSelectedZeitraum(geloeschterZeitraum);

        // Test
        controller.loescheZeitraum(model);

        assertNotNull(model);
        assertNotNull(model.getTage());
        assertEquals(anzahlZeitraeume - 1, model.getTage().get(0).getZeitraeume().size());
        assertFalse(model.getTage().get(0).getZeitraeume().contains(geloeschterZeitraum));
    }

    @Test
    public void testValidiereStammdatenErfolgreich() throws TerminfindungBusinessException {
        // Vorbereitung des Testobjekts
        ErstellenController controller = new ErstellenController();
        AwkWrapper awkWrapper = mock(AwkWrapper.class);
        controller.setAwk(awkWrapper);

        // Vorbereitung der Testdaten
        ErstellenModel model = new ErstellenModel();
        model.setName(VERANSTALTUNG_NAME);
        model.setOrgName(ORGANISATOR_NAME);

        // Test
        assertTrue(controller.validiereStammdaten(model));

        verifyZeroInteractions(awkWrapper);

        assertNull(model.getTerminfindung());
    }

    @Test
    public void testValidiereStammdatenOrganisatorUngueltig() throws TerminfindungBusinessException {
        // Vorbereitung des Testobjekts
        ErstellenController controller = new ErstellenController();

        GlobalFlowController globalFlowController = new GlobalFlowController();
        ValidationController validationController = mock(ValidationController.class);
        globalFlowController.setValidationController(validationController);
        controller.setGlobalFlowController(globalFlowController);

        AwkWrapper awkWrapper = mock(AwkWrapper.class);
        controller.setAwk(awkWrapper);

        // Vorbereitung der Testdaten
        ErstellenModel model = new ErstellenModel();
        model.setName(VERANSTALTUNG_NAME);

        // Test
        assertFalse(controller.validiereStammdaten(model));

        verifyZeroInteractions(awkWrapper);

        verify(validationController).processValidationMessages(anyListOf(ValidationMessage.class));
        verifyNoMoreInteractions(validationController);

        assertNull(model.getTerminfindung());
    }

    @Test
    public void testValidiereStammdatenNameUngueltig() throws TerminfindungBusinessException {
        // Vorbereitung des Testobjekts
        ErstellenController controller = new ErstellenController();

        GlobalFlowController globalFlowController = new GlobalFlowController();
        ValidationController validationController = mock(ValidationController.class);
        globalFlowController.setValidationController(validationController);
        controller.setGlobalFlowController(globalFlowController);

        AwkWrapper awkWrapper = mock(AwkWrapper.class);
        controller.setAwk(awkWrapper);

        // Vorbereitung der Testdaten
        ErstellenModel model = new ErstellenModel();
        model.setOrgName(ORGANISATOR_NAME);

        // Test
        assertFalse(controller.validiereStammdaten(model));

        verifyZeroInteractions(awkWrapper);

        verify(validationController).processValidationMessages(anyListOf(ValidationMessage.class));
        verifyNoMoreInteractions(validationController);

        assertNull(model.getTerminfindung());
    }

    @Test
    public void testValidiereTermineErfolgreich() throws TerminfindungBusinessException {
        // Vorbereitung des Testobjekts
        ErstellenController controller = new ErstellenController();
        AwkWrapper awkWrapper = mock(AwkWrapper.class);
        controller.setAwk(awkWrapper);

        initialisiereAwkWrapper(awkWrapper);

        // Vorbereitung der Testdaten
        TagModel termin = GuiTestdaten.erstelleTermin();
        int anzahlZeitraeume = termin.getZeitraeume().size();

        ErstellenModel model = new ErstellenModel();
        model.setName(VERANSTALTUNG_NAME);
        model.setOrgName(ORGANISATOR_NAME);
        model.getTage().add(termin);

        // Test
        assertTrue(controller.validiereTermine(model));

        verify(awkWrapper).erstelleTerminfindung(ORGANISATOR_NAME, VERANSTALTUNG_NAME, model.getTage());
        verifyNoMoreInteractions(awkWrapper);

        assertNotNull(model.getTerminfindung());
        assertNotNull(model.getTage());
        assertEquals(anzahlZeitraeume, model.getTage().get(0).getZeitraeume().size());
        for (ZeitraumModel zeitraum : termin.getZeitraeume()) {
            assertTrue(model.getTerminfindung().getTage().get(0).getZeitraeume().contains(zeitraum));
        }
    }

    @Test
    public void testValidiereTermineMitAwkAusnahme() throws TerminfindungBusinessException {
        // Vorbereitung des Testobjekts
        ErstellenController controller = new ErstellenController();
        AwkWrapper awkWrapper = mock(AwkWrapper.class);
        controller.setAwk(awkWrapper);

        when(awkWrapper.erstelleTerminfindung(anyString(), anyString(), anyListOf(TagModel.class))).thenThrow(TerminfindungBusinessException.class);

        // Vorbereitung der Testdaten
        ErstellenModel model = new ErstellenModel();
        model.setName(VERANSTALTUNG_NAME);
        model.setOrgName(ORGANISATOR_NAME);
        model.getTage().add(GuiTestdaten.erstelleTermin());

        // Test
        assertFalse(controller.validiereTermine(model));

        verify(awkWrapper).erstelleTerminfindung(ORGANISATOR_NAME, VERANSTALTUNG_NAME, model.getTage());
        verifyNoMoreInteractions(awkWrapper);
    }

    @Test
    public void testValidiereTermineMitFehlern() throws TerminfindungBusinessException {
        // Vorbereitung des Testobjekts
        ErstellenController controller = new ErstellenController();

        GlobalFlowController globalFlowController = new GlobalFlowController();
        globalFlowController.setValidationController(mock(ValidationController.class));
        controller.setGlobalFlowController(globalFlowController);

        AwkWrapper awkWrapper = mock(AwkWrapper.class);
        controller.setAwk(awkWrapper);

        initialisiereAwkWrapper(awkWrapper);

        // Vorbereitung der Testdaten
        ErstellenModel model = new ErstellenModel();
        model.setName(VERANSTALTUNG_NAME);
        model.setOrgName(ORGANISATOR_NAME);
        model.getTage().add(GuiTestdaten.erstelleTermin());
        model.getTage().get(0).getZeitraeume().clear();

        // Test
        assertFalse(controller.validiereTermine(model));

        verifyZeroInteractions(awkWrapper);
    }

}