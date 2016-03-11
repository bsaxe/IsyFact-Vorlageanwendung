package de.msg.terminfindung.gui.util;

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


import org.springframework.webflow.engine.FlowExecutionExceptionHandler;
import org.springframework.webflow.engine.RequestControlContext;
import org.springframework.webflow.execution.FlowExecutionException;

import java.io.Serializable;

/**
 * Einfacher Exception Handler zu Ausgabe von Flow Exceptions auf der Konsole
 *
 * @author msg systems ag, Dirk Jäger
 */
public class SimpleFlowExecutionExceptionHandler implements FlowExecutionExceptionHandler, Serializable {

    private static final long serialVersionUID = -477074570145205726L;

    @Override
    public boolean canHandle(FlowExecutionException exception) {

        // Kein Handling der Exception, aber Ausgabe der Meldung
        System.out.println(exception.getMessage());
        exception.printStackTrace();
        return false;
    }

    @Override
    public void handle(FlowExecutionException exception, RequestControlContext context) {
    }
}
