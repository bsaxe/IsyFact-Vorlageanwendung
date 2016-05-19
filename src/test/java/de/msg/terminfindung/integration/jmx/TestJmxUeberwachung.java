package de.msg.terminfindung.integration.jmx;

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

import de.msg.terminfindung.common.konstanten.TestProfile;
import de.msg.terminfindung.gui.terminfindung.erstellen.ErstellenController;
import de.msg.terminfindung.gui.terminfindung.erstellen.ErstellenModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/app-context.xml", "classpath:/spring/gui/controller.xml"})
@ActiveProfiles(TestProfile.INTEGRATION_TEST)
public class TestJmxUeberwachung {

    @Autowired
    ErstellenController erstellenController;

    @Test
    public void testJmxUeberwachung() throws Exception {
        // Holen des MBeanServers
        ArrayList<MBeanServer> mBeanServerList = MBeanServerFactory.findMBeanServer(null);
        MBeanServer mBeanServer = mBeanServerList.get(0);

        // Lesen der gewünschten Information per JMX
        Hashtable<String, String> table = new Hashtable<>();
        table.put("type", "ServiceStatistik");
        table.put("name", "\"Erstellung-Statistik\"");
        ObjectName testObjectName = new ObjectName("de.bund.bva.isyfact.terminfindung", table);
        String testAttributeName = "DurchschnittsDauerLetzteAufrufe";
        String result = mBeanServer.getAttribute(testObjectName, testAttributeName).toString();

        // Auswerten des Ergebnisses
        assertEquals("0", result);

        // Einen Anwendungsfall ausführen
        erstellenController.initialisiereModel(new ErstellenModel());
        result = mBeanServer.getAttribute(testObjectName, testAttributeName).toString();
        assertNotEquals("0", result);
    }
}
