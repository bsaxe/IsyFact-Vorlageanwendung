package de.msg.terminfindung.gui;

import de.msg.terminfindung.common.exception.TerminfindungBusinessException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.webflow.engine.FlowExecutionExceptionHandler;
import org.springframework.webflow.engine.support.TransitionExecutingFlowExecutionExceptionHandler;

/**
 * Zusätzliche Konfiguration von Spring Web Flow.
 *
 * @author Stefan Dellmuth, msg systems ag
 */
@Configuration
public class WebFlowConfiguration {

    /**
     * Erstellt einen Exception Handler für Flows. Fachliche Exceptions während der Ausführung von Flows werden auf
     * einen einheitlichen Fehlerzustand umgeleitet, der im Parent Flow der Anwendung definiert ist.
     *
     * @return den Exception Handler.
     */
    @Bean(name = "flowExceptionHandler")
    public FlowExecutionExceptionHandler flowExecutionExceptionHandler() {
        TransitionExecutingFlowExecutionExceptionHandler handler = new TransitionExecutingFlowExecutionExceptionHandler();

        handler.add(TerminfindungBusinessException.class, "fachlicherFehlerState");

        return handler;
    }

}
