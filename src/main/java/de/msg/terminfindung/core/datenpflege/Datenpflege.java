package de.msg.terminfindung.core.datenpflege;

import java.util.Date;

/**
 * Schnittstelle der Anwendungskomponente "Datenpflege" zur Pflege der Bestandsdaten
 *
 * @author msg systems ag, Stefan Dellmuth
 */
public interface Datenpflege {

    void loescheVergangeneTerminfindungen(Date loeschFrist);

}
