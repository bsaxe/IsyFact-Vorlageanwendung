package de.msg.terminfindung.gui;

/*
 * #%L
 * Terminfindung
 * %%
 * Copyright (C) 2015 - 2016 Bundesverwaltungsamt (BVA), msg systems ag
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
