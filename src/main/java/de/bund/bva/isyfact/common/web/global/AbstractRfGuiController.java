package de.bund.bva.isyfact.common.web.global;

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


@SuppressWarnings("rawtypes")
public abstract class AbstractRfGuiController extends AbstractGuiController {

	// Diese Klasse dient dazu, AbstractGuiController unter dem Namen AbstractRfGuiController
	// unverändert noch einmal zur Verfügung zu stellen und damit einen Fehler
	// in der Konfigurationsdatei ./resources/plis-web/spring/controller.xml in plis-web 3.0.40 zu kompensieren.
	// Dort wurde der AbstractGuiController unter dem (anscheinend) falschen/obsoleten Namen konfiguriert
	
}
