package de.msg.terminfindung.sicherheit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Implementierung des AccessReferenceMap Interfaces. Der Typ der direkten Referenz ist {@link java.lang.Long}.
 * Als indirekte Referenz werden per {@link java.util.UUID#randomUUID()} generierte UUIDs verwendet.
 * 
 * @author Björn Saxe, msg systems ag
 *
 */
public class UuidToLongAccessReferenceMap implements AccessReferenceMap<Long>
{	
	private Map<Long, String> directToIndirectMap;
	private Map<String, Long> indirectToDirectMap;
	
	public UuidToLongAccessReferenceMap()
	{
		directToIndirectMap = new HashMap<>();
		indirectToDirectMap = new HashMap<>();
	}

	@Override
	public Long getDirectReference(String indirectReference)
	{		
		return indirectToDirectMap.get(indirectReference);
	}

	@Override
	public String getIndirectReference(Long directReference)
	{		
		return directToIndirectMap.get(directReference);
	}

	@Override
	public String addDirectReference(Long directReference)
	{
		checkForNullArgument(directReference);
		
		String indirectRef = directToIndirectMap.get(directReference);
		if (indirectRef == null)
		{
			indirectRef = UUID.randomUUID().toString();
			directToIndirectMap.put(directReference, indirectRef.toString());
			indirectToDirectMap.put(indirectRef.toString(), directReference);
		}
		
		return indirectRef.toString();
	}

	@Override
	public String removeDirectReference(Long directReference)
	{
		checkForNullArgument(directReference);
		
		String indirectRef = directToIndirectMap.remove(directReference);
		indirectToDirectMap.remove(indirectRef);
		
		return indirectRef;
	}
	
	private void checkForNullArgument(Object o)
	{
		if (o == null)
			throw new IllegalArgumentException("argument must not be null");
	}
}
