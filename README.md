# IsyFact-Vorlageanwendung
Die Vorlageanwendung der IsyFact zeigt anhand einer Anwendung zur gemeinsamen Planung von Terminen die Möglichkeiten der IsyFact. Sie bietet dadurch ein leichten Einstieg in die Entwicklung mit der IsyFact und steht sowohl als Leitfaden als auch als Nachschlagewerk für die Entwicklung weiterer Anwendungen auf Basis der IsyFact zur Verfügung.

**Wichtig:** Der aktuelle Stand der Anwendung entspricht dem einer Alpha-Version. Die Anwendung ist lauffähig und demonstriert bereits den Einsatz von Teilen der IsyFact-Standards. Sie ist allerdings nicht als fertig zu betrachten und kann möglicherweise gravierende Fehler beinhalten. Die Vorlageanwendung wird ständig von einem gemeinsamen Team des Bundesverwaltungsamts und der msg systems ag weiterentwickelt.

## Entwicklungsumgebung
In Kürze finden Sie hier eine Beschreibung, wie Sie eine Entwicklungsumgebung für die Vorlageanwendung aufbauen.

### Spring-Profile
Die Vorlageanwendung stellt einige Spring-Profile bereit, um die Entwicklungsarbeit leichter zu gestalten:
* `produktion`: Ist als Standard voreingestellt und startet die Anwendung unter Produktionsbedingungen.
* `test-unit`: Wird für die Ausführung von Komponententests benutzt. Verwendet eine In-Memory-Datenbank.
* `test-integration`: Wird für die Ausführung von Integrationstests benutzt. Startet die komplette Anwendung, verwendet aber eine In-Memory-Datenbank (H2) und deaktiviert den Selbsttest sowie die Beobachtung von Änderungen der betrieblichen Konfiguration zur Laufzeit.
* `entwicklung`: Ist für den Betrieb innerhalb einer Entwicklungsumgebung vorgesehen. Verwendet eine H2-Datenbank und deaktiviert die Überwachung der Anwendung, den Selbsttest sowie die Beobachtung von Änderungen der betrieblichen Konfiguration zur Laufzeit.

### Maven-Profile
Die Vorlageanwendung definiert das Maven-Profil `entwicklung`, das im Unterschied zum Standard-Profil eine In-Memory-Datenbank (H2) als Abhängigkeit definiert. Das Profil wird während der Entwicklung und zur Durchführung von Komponenten- sowie Integrationstests benutzt.

## IsyFact
Die IsyFact ist eine Software Factory für den Bau von komplexen IT-Anwendungslandschaften, die vom Bundesverwaltungsamt entwickelt wurde. Sie bündelt bestehendes technisches Know-how um Anwendungssysteme effizienter entwickeln und betreiben zu können.

Weiterführende Informationen erhalten Sie auf: <http://www.isyfact.de/>

## Lizenz
Copyright &copy; 2015 - 2016 Bundesverwaltungsamt (BVA), msg systems ag.

Licensed under the Apache License, Version 2.0 (the "License"). You may obtain a copy of the License at:

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

## Kontakt
* Homepage:  <http://www.isyfact.de/>
* E-Mail: isyfact@bva.bund.de
