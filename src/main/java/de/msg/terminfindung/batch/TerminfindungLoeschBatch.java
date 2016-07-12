package de.msg.terminfindung.batch;

import de.bund.bva.isyfact.logging.IsyLogger;
import de.bund.bva.isyfact.logging.IsyLoggerFactory;
import de.bund.bva.isyfact.logging.LogKategorie;
import de.bund.bva.pliscommon.batchrahmen.batch.exception.BatchAusfuehrungsException;
import de.bund.bva.pliscommon.batchrahmen.batch.konfiguration.BatchKonfiguration;
import de.bund.bva.pliscommon.batchrahmen.batch.protokoll.BatchErgebnisProtokoll;
import de.bund.bva.pliscommon.batchrahmen.batch.rahmen.AuthenticationCredentials;
import de.bund.bva.pliscommon.batchrahmen.batch.rahmen.BatchAusfuehrungsBean;
import de.bund.bva.pliscommon.batchrahmen.batch.rahmen.BatchStartTyp;
import de.bund.bva.pliscommon.batchrahmen.batch.rahmen.VerarbeitungsErgebnis;
import de.bund.bva.pliscommon.konfiguration.common.Konfiguration;
import de.bund.bva.pliscommon.util.spring.MessageSourceHolder;
import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import de.msg.terminfindung.common.konstanten.EreignisSchluessel;
import de.msg.terminfindung.common.konstanten.KonfigurationSchluessel;
import de.msg.terminfindung.core.datenpflege.Datenpflege;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Batch zum Löschen von Terminen, die eine gewisse Zeit zurückliegen. Die genaue Zeitspanne wird in der betrieblichen
 * Konfiguration festgelegt.
 *
 * @author Stefan Dellmuth, msg systems
 */
public class TerminfindungLoeschBatch implements BatchAusfuehrungsBean {

    private static final IsyLogger LOG = IsyLoggerFactory.getLogger(TerminfindungLoeschBatch.class);

    private final Datenpflege datenpflege;

    private final Konfiguration betrieblicheKonfiguration;

    private Date loeschfrist;

    private boolean testmodus;

    public TerminfindungLoeschBatch(Datenpflege datenpflege, Konfiguration konfiguration) {
        this.datenpflege = datenpflege;
        betrieblicheKonfiguration = konfiguration;
    }

    @Override
    public int initialisieren(BatchKonfiguration konfiguration, long satzNummer, String dbKey, BatchStartTyp startTyp,
                              Date datumLetzterErfolg, BatchErgebnisProtokoll protokoll) throws BatchAusfuehrungsException {

        testmodus = leseWahrheitswert(KonfigurationSchluessel.BATCH_TESTMODUS, konfiguration);

        long loeschfristInTagen = leseZahl(KonfigurationSchluessel.BATCH_FRIST_LOESCHEN, konfiguration);

        // Heute um Mitternacht
        Calendar loeschfristBerechnung = Calendar.getInstance();
        loeschfristBerechnung.set(Calendar.HOUR_OF_DAY, 0);
        loeschfristBerechnung.set(Calendar.MINUTE, 0);
        loeschfristBerechnung.set(Calendar.SECOND, 0);
        loeschfristBerechnung.set(Calendar.MILLISECOND, 0);

        // Ermittle Löschfrist
        loeschfristBerechnung.add(Calendar.DAY_OF_MONTH, (int) -loeschfristInTagen);

        loeschfrist = loeschfristBerechnung.getTime();

        boolean restart = startTyp == BatchStartTyp.RESTART;
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String startMeldung = MessageSourceHolder.getMessage("batch.loeschen.start", format.format(loeschfrist));
        if (restart) {
            LOG.info(LogKategorie.JOURNAL, EreignisSchluessel.MSG_BATCH_START_WIEDERHOLUNG, startMeldung);
        } else {
            LOG.info(LogKategorie.JOURNAL, EreignisSchluessel.MSG_BATCH_START_NEU, startMeldung);
        }
        return 0;
    }

    @Override
    public VerarbeitungsErgebnis verarbeiteSatz() throws BatchAusfuehrungsException {
        if (!testmodus) {
            try {
                datenpflege.loescheVergangeneTerminfindungen(loeschfrist);
            } catch (TerminfindungBusinessException tbe) {
                throw new BatchAusfuehrungsException(tbe);
            }
        }

        return new VerarbeitungsErgebnis(null, true);
    }

    @Override
    public void batchBeendet() {
        LOG.info(LogKategorie.JOURNAL, EreignisSchluessel.MSG_BATCH_BEENDET, "Löschbatch beendet.");
    }

    @Override
    public void checkpointGeschrieben(long satzNummer) throws BatchAusfuehrungsException {
        LOG.info(LogKategorie.JOURNAL, EreignisSchluessel.MSG_BATCH_CHECKPOINT, "Löschbatch: Checkpoint geschrieben.");
    }

    @Override
    public void vorCheckpointGeschrieben(long satzNummer) throws BatchAusfuehrungsException {
        // Keine Aktion nötig.
    }

    @Override
    public void rollbackDurchgefuehrt() {
        LOG.info(LogKategorie.JOURNAL, EreignisSchluessel.MSG_BATCH_CHECKPOINT, "Löschbatch: Rollback durchgeführt.");
    }

    @Override
    public void vorRollbackDurchgefuehrt() {
        // Keine Aktion nötig.
    }

    @Override
    public AuthenticationCredentials getAuthenticationCredentials(BatchKonfiguration konfiguration) {
        // Lade Nutzer aus Kommandozeile / betrieblicher Konfiguration
        AuthenticationCredentials auth = new AuthenticationCredentials();
        auth.setBehoerdenkennzeichen(leseZeichenkette(KonfigurationSchluessel.BATCH_ORGANISATION, konfiguration));
        auth.setBenutzerkennung(leseZeichenkette(KonfigurationSchluessel.BATCH_BENUTZER, konfiguration));
        auth.setPasswort(leseZeichenkette(KonfigurationSchluessel.BATCH_PASSWORT, konfiguration));

        return auth;
    }

    private Boolean leseWahrheitswert(String parameterName, BatchKonfiguration konfiguration) {
        boolean defaultWert = betrieblicheKonfiguration.getAsBoolean(parameterName);
        return konfiguration.getAsBoolean(parameterName, defaultWert);
    }

    private String leseZeichenkette(String parameterName, BatchKonfiguration konfiguration) {
        String defaultWert = betrieblicheKonfiguration.getAsString(parameterName);
        return konfiguration.getAsString(parameterName, defaultWert);
    }

    private Long leseZahl(String parameterName, BatchKonfiguration konfiguration) {
        Long defaultWert = betrieblicheKonfiguration.getAsLong(parameterName);
        return konfiguration.getAsLong(parameterName, defaultWert);
    }
}
