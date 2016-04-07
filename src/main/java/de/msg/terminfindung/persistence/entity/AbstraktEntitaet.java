package de.msg.terminfindung.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Basisklasse aller Entitäten der Anwendung. Alle Entitäten enthalten einen Primärschlüssel des gleichen Typs und mit
 * der gleichen Erzeugungsstrategie. Je nach Anwendung können hier auch weitere Felder aufgenommen werden, z.B. zum
 * Auditing.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstraktEntitaet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
