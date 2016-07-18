package de.msg.terminfindung.sicherheit;

/**
 * Das AccessReferenceMap Interface wird dazu benutzt, um aus internen direkten Objektreferenzen
 * (z.B. Primärschlüssel aus Datenbanken) eine Zuordnung auf indirekte Referenzen (z.B. UUIDs)
 * herzustellen, die sicher öffentlich angezeigt werden können. Indirekte Referenzen werden als
 * Strings gehandhabt, um ihre Verwendung in HTML zu vereinfachen.
 * 
 * @author Björn Saxe, msg systems ag
 *
 * @param <D> Der Typ der direkten Objektreferenz
 */
public interface AccessReferenceMap<D>
{
	/**
	 * Gibt die mit der indirekten Referenz verknüpfte direkte Referenz zurück.
	 * 
	 * @param indirectReference Die indirekte Referenz.
	 * @return Die mit der indirekten Referenz verknüpfte direkte Referenz.
	 */
	D getDirectReference(String indirectReference);
	
	/**
	 * Gibt die mit der direkten Referenz verknüpfte indirekte Referenz zurück.
	 * 
	 * @param directReference Die direkte Referenz.
	 * @return Die mit der direkten Referenz verknüpfte indirekte Referenz.
	 */
	String getIndirectReference(D directReference);
	
	/**
	 * Fügt eine direkte Referenz zur AccessReferenceMap hinzu, generiert eine damit verknüpfte
	 * indirekte Referenz und gibt diese zurück. Ist bereits eine Zuordnung für die direkte Referenz
	 * vorhanden, wird keine neue indirekte Referenz hinzugefügt und die alte Zuordnung zurückgegeben.
	 * 
	 * @param directReference Die direkte Referenz.
	 * @return Die mit der direkten Referenz verknüpfte indirekte Referenz.
	 */
	String addDirectReference(D directReference);
	
	/**
	 * Entfernt die Zuordnung zwischen der gegebenen direkten und der indirekten Referenz.
	 * 
	 * @param directReference Die direkte Referenz.
	 * @return Die mit der direkten Referenz verknüpfte indirekte Referenz.
	 */
	String removeDirectReference(D directReference);
}
