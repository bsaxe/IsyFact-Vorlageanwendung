package de.msg.terminfindung.persistence.dao;

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


import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import de.msg.terminfindung.persistence.entity.Organisator;
import de.msg.terminfindung.persistence.entity.Terminfindung;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

import java.util.List;

public class TerminfindungDaoTest extends AbstraktDaoTest {

    private static final Long TERMINFINDUNG_ID = 2L;

    @Autowired
    private TerminfindungDao terminfindungDao;

    @Test
    @DatabaseSetup("testTerminfindungDaoSetup.xml")
    @ExpectedDatabase(value = "testTerminfindungDaoSpeichern.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testSpeichern() {
        terminfindungDao.speichere(new Terminfindung("Grillfest 2016", new Organisator("Sepp")));
    }

    @Test
    @DatabaseSetup("testTerminfindungDaoSetup.xml")
    public void testSuchenMitId() {
        Terminfindung terminfindung = terminfindungDao.sucheMitId(TERMINFINDUNG_ID);

        assertNotNull(terminfindung);
        assertEquals("Herbert", terminfindung.getOrganisator().getName());
        assertEquals("Spieleabend IsyFact", terminfindung.getVeranstaltungName());
    }
    
    @Test
    @DatabaseSetup("testTerminfindungDaoSetup.xml")
    public void testFindeAlle()
    {
    	List<Terminfindung> alleTerminfindungen = terminfindungDao.findeAlle();
    	
    	assertNotNull(alleTerminfindungen);
    	assertEquals(2, alleTerminfindungen.size());
        assertEquals("Klaus", alleTerminfindungen.get(0).getOrganisator().getName());
        assertEquals("Weihnachtsfeier 2016", alleTerminfindungen.get(0).getVeranstaltungName());
        assertEquals("Herbert", alleTerminfindungen.get(1).getOrganisator().getName());
        assertEquals("Spieleabend IsyFact", alleTerminfindungen.get(1).getVeranstaltungName());
    }

    @Test
    @DatabaseSetup("testTerminfindungDaoSetup.xml")
    @ExpectedDatabase(value = "testTerminfindungDaoLoeschen.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testLoesche() {
        Terminfindung terminfindung = terminfindungDao.sucheMitId(TERMINFINDUNG_ID);
        terminfindungDao.loesche(terminfindung);

        assertNull(terminfindungDao.sucheMitId(TERMINFINDUNG_ID));
    }


}
