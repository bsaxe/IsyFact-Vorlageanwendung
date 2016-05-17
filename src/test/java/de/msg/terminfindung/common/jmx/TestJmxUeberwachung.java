package de.msg.terminfindung.common.jmx;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.msg.terminfindung.gui.terminfindung.erstellen.ErstellenController;
import de.msg.terminfindung.gui.terminfindung.erstellen.ErstellenModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/app-context.xml", "classpath:/spring/gui/controller.xml"})
@ActiveProfiles("entwicklung")
public class TestJmxUeberwachung {

	@Autowired
	ErstellenController contr;
	
	@Test
	public void testJmxUeberwachung() throws Exception {
		// SpringContext initialisieren und Komponente holen
		//Meldung meldung = (Meldung) SpringContextHolder.getBean("meldung");
		// Holen des MBeanServers
		ArrayList<MBeanServer> mBeanServerList = MBeanServerFactory.findMBeanServer(null);
		MBeanServer mBeanServer = mBeanServerList.get(0);	
		// Lesen der gewünschten Information per JMX
		// de.bund.bva.isyfact.terminfindung:type=ServiceStatistik,name=&quot;Erstellung-Statistik&quot;
		Hashtable<String, String> table = new Hashtable<>();
		table.put("type", "ServiceStatistik");
		table.put("name", "\"Erstellung-Statistik\"");
		ObjectName testObjectName = new ObjectName(
		"de.bund.bva.isyfact.terminfindung", table);
		String testAttributeName = "DurchschnittsDauerLetzteAufrufe";
		String result = mBeanServer.getAttribute(
		testObjectName, testAttributeName).toString();
		// Auswerten des Ergebnisses
		assertEquals("0", result);			
		// Einen Anwendungsfall ausführen
		contr.initialisiereModel(new ErstellenModel());	
		result = mBeanServer.getAttribute(
				testObjectName, testAttributeName).toString();
		assertNotEquals("0", result);		
	}
}
