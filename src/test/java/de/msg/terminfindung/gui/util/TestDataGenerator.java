package de.msg.terminfindung.gui.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TestDataGenerator {

	@Test
	public void testGetZeitraumAuswahl() {
		List<String> zeiten = DataGenerator.getUhrzeitAuswahl();
		assertEquals("00:00", zeiten.get(0));
		assertEquals("00:15", zeiten.get(1));
		assertEquals("00:30", zeiten.get(2));
		assertEquals("00:45", zeiten.get(3));
		assertEquals("01:00", zeiten.get(4));
		assertEquals("01:15", zeiten.get(5));
		assertEquals("23:45", zeiten.get(95));
	}

}
