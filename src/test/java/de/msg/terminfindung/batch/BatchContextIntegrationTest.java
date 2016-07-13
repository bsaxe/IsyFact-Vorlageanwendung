package de.msg.terminfindung.batch;

import de.msg.terminfindung.common.konstanten.TestProfile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Testet die Erstellung des Batch-Kontextes im Integrationstest. Dies ist Voraussetzung für das Gelingen der
 * Batchtests.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/app-context-batch.xml"})
@ActiveProfiles(TestProfile.INTEGRATION_TEST)
public class BatchContextIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void kontextHochgefahren() {
        Assert.assertNotNull(applicationContext);
    }
}
