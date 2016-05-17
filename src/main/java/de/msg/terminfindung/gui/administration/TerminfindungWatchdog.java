package de.msg.terminfindung.gui.administration;

import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.ueberwachung.admin.impl.WatchdogImpl;
import org.springframework.beans.factory.InitializingBean;

import javax.persistence.EntityManager;
import java.util.concurrent.Callable;

public class TerminfindungWatchdog extends WatchdogImpl implements InitializingBean {

    /**
     * SQL-Query, welche der Watchdog zur Prüfung der Datenbankverbindung ausführt.
     */
    private static final String CONF_ADMIN_WATCHDOG_VALIDATION_QUERY = "admin.watchdog.validation.query";

    private Konfiguration konfiguration;

    private EntityManager entityManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        addPruefung("Datenbank", new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {            	
                final String watchdogQuery = konfiguration.getAsString(CONF_ADMIN_WATCHDOG_VALIDATION_QUERY);
                entityManager.createNativeQuery(watchdogQuery).getSingleResult();
                return true;
            }
        });
    }

    public void setKonfiguration(Konfiguration konfiguration) {
        this.konfiguration = konfiguration;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
