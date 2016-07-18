package de.msg.terminfindung.sicherheit;

import de.msg.terminfindung.sicherheit.UuidToLongAccessReferenceMap;
import org.junit.Assert;

import org.junit.Test;

public class UuidToLongAccessReferenceMapTest {

	@Test
	public void testGetDirectReference() {
		UuidToLongAccessReferenceMap map = new UuidToLongAccessReferenceMap();
		String indirectRef = map.addDirectReference(1L);

		Assert.assertEquals(1L, (long) map.getDirectReference(indirectRef));
	}

	@Test
	public void testGetIndirectReference() {
		UuidToLongAccessReferenceMap map = new UuidToLongAccessReferenceMap();
		String expectedIndirectRef = map.addDirectReference(1L);

		Assert.assertEquals(expectedIndirectRef, map.getIndirectReference(1L));
	}

	@Test
	public void testAddDirectReference() {
		UuidToLongAccessReferenceMap map = new UuidToLongAccessReferenceMap();
		String indirectReference = map.addDirectReference(1L);

		Assert.assertNotNull(map.getDirectReference(indirectReference));
	}

	@Test
	public void testAddExistingDirectReferenceReturnsExistingIndirectReference() {
		UuidToLongAccessReferenceMap map = new UuidToLongAccessReferenceMap();
		String indirectRef = map.addDirectReference(1L);
		String secondIndirectRef = map.addDirectReference(1L);

		Assert.assertEquals(indirectRef, secondIndirectRef);
	}

	@Test
	public void testRemoveDirectReference() {
		UuidToLongAccessReferenceMap map = new UuidToLongAccessReferenceMap();
		String indirectRef = map.addDirectReference(1L);
		Assert.assertEquals(indirectRef, map.removeDirectReference(1L));
		Assert.assertNull(map.getDirectReference(indirectRef));
		Assert.assertNull(map.removeDirectReference(1L));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddDirectReferenceWithNullArgumentThrowsException() {
		UuidToLongAccessReferenceMap map = new UuidToLongAccessReferenceMap();
		map.addDirectReference(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveDirectReferenceWithNullArgumentThrowsException() {
		UuidToLongAccessReferenceMap map = new UuidToLongAccessReferenceMap();
		map.removeDirectReference(null);
	}
}
