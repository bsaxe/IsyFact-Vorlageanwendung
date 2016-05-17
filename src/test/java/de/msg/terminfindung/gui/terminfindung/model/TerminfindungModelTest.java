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

import de.msg.terminfindung.gui.testdaten.GuiTestdaten;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(BlockJUnit4ClassRunner.class)
public class TerminfindungModelTest {
    @Test
    public void testFindeZeitraumByIdModel() {
        TerminfindungModel tfModel = new TerminfindungModel();
        TagModel tagModel = new TagModel();
        ZeitraumModel zeitraumModel1 = new ZeitraumModel();
        ZeitraumModel zeitraumModel2 = new ZeitraumModel();

        zeitraumModel1.setId(1L);
        zeitraumModel2.setId(22L);

        tagModel.getZeitraeume().add(zeitraumModel1);
        tagModel.getZeitraeume().add(zeitraumModel2);

        tfModel.getTage().add(tagModel);


        assertNull(tfModel.findeZeitraumById(0));
        assertNotNull(tfModel.findeZeitraumById(22));

    }

    @Test
    public void testFindeZeitraumByIdZeitraeumeNullModel() {
        TerminfindungModel tfModel = new TerminfindungModel();
        TagModel tagModel = new TagModel();
        tagModel.setZeitraeume(null);

        tfModel.getTage().add(tagModel);


        assertNull(tfModel.findeZeitraumById(0));
    }

    @Test
    public void testFindeZeitraumByIdTageNullModel() {
        TerminfindungModel tfModel = new TerminfindungModel();
        tfModel.setTage(null);

        assertNull(tfModel.findeZeitraumById(0));
    }

    @Test
    public void testExistsTeilnehmerName() {
        TerminfindungModel tfModel = GuiTestdaten.erstelleTerminfindung();

        assertTrue(tfModel.existsTeilnehmerName("Klaus"));
        assertFalse(tfModel.existsTeilnehmerName("Jochen"));
        assertFalse(tfModel.existsTeilnehmerName(null));
        assertFalse(tfModel.existsTeilnehmerName(""));
    }

    @Test
    public void testExistsTeilnehmerNameNull() {
        TerminfindungModel tfModel = new TerminfindungModel();
        tfModel.setTeilnehmer(null);

        assertFalse(tfModel.existsTeilnehmerName("Klaus"));
    }

    @Test
    public void testGetAlleZeitraeume() {
        TerminfindungModel tfModel = GuiTestdaten.erstelleTerminfindung();

        List<ZeitraumModel> zeitraeume = tfModel.getAlleZeitraeume();
        assertNotNull(zeitraeume);
        assertEquals(2, zeitraeume.size());
    }

    @Test
    public void testGetAlleZeitraeumeLeereListe() {
        TerminfindungModel tfModel = GuiTestdaten.erstelleTerminfindung();
        tfModel.getTage().clear();

        List<ZeitraumModel> zeitraeume = tfModel.getAlleZeitraeume();
        assertNotNull(zeitraeume);
        assertTrue(zeitraeume.isEmpty());
    }

    @Test
    public void testGetAlleZeitraeumeListeNull() {
        TerminfindungModel tfModel = new TerminfindungModel();
        TagModel tagModel = new TagModel();
        tagModel.setZeitraeume(null);

        tfModel.getTage().add(tagModel);

        List<ZeitraumModel> zeitraeume = tfModel.getAlleZeitraeume();
        assertNotNull(zeitraeume);
        assertTrue(zeitraeume.isEmpty());
    }

    @Test
    public void testGetAlleZeitraeumeNull() {
        TerminfindungModel tfModel = GuiTestdaten.erstelleTerminfindung();
        tfModel.setTage(null);

        List<ZeitraumModel> zeitraeume = tfModel.getAlleZeitraeume();
        assertNotNull(zeitraeume);
        assertTrue(zeitraeume.isEmpty());
    }

    @Test
    public void testGetTeilnehmerLabel() {
        TerminfindungModel tfModel = GuiTestdaten.erstelleTerminfindung();

        assertEquals("Hans, Heinz, Klaus", tfModel.getTeilnehmerLabel());
    }

    @Test
    public void testGetTeilnehmerLabelNull() {
        TerminfindungModel tfModel = new TerminfindungModel();
        tfModel.setTeilnehmer(null);

        assertEquals("", tfModel.getTeilnehmerLabel());
    }


    @Test
    public void testGetTeilnehmerLabelPraeferenz() {
        TerminfindungModel tfModel = GuiTestdaten.erstelleTerminfindung();

        List<ZeitraumModel> zeitraum = tfModel.getTage().get(0).getZeitraeume();
        assertEquals("Klaus", tfModel.getTeilnehmerLabel(zeitraum.get(0), 0));
    }

}
