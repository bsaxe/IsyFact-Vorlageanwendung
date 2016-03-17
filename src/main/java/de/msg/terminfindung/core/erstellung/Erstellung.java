package de.msg.terminfindung.core.erstellung;

import java.util.List;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.persistence.entity.Tag;
import de.msg.terminfindung.persistence.entity.Terminfindung;

/**
 * Interface der Anwendungskomponente "Erstellung" zur Erstellung von Terminfindungen
 * @author bva Vadim Richter
 */
public interface Erstellung {

	Terminfindung 	erstelleTerminfindung(String orgName,String veranstName,List<Tag> tagList) throws TerminfindungBusinessException;
}
