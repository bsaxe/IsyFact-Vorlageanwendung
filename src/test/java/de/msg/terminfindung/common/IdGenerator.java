package de.msg.terminfindung.common;

/**
 * Generiert IDs für Testdaten.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
public class IdGenerator {

    private Long nextId;

    public IdGenerator() {
        this(1L);
    }

    public IdGenerator(Long startId) {
        nextId = startId;
    }

    public Long nextId() {
        return nextId++;
    }

}
