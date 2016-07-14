rem #######################################################################
rem # #%L
rem # plis-persistence
rem # %%
rem # 
rem # %%
rem # See the NOTICE file distributed with this work for additional
rem # information regarding copyright ownership.
rem # The Federal Office of Administration (Bundesverwaltungsamt, BVA)
rem # licenses this file to you under the Apache License, Version 2.0 (the
rem # License). You may not use this file except in compliance with the
rem # License. You may obtain a copy of the License at
rem # 
rem #     http://www.apache.org/licenses/LICENSE-2.0
rem # 
rem # Unless required by applicable law or agreed to in writing, software
rem # distributed under the License is distributed on an "AS IS" BASIS,
rem # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
rem # implied. See the License for the specific language governing
rem # permissions and limitations under the License.
rem # #L%
rem #
rem # Aktualisiert die Datenbank für das System XXX_PLATZHALTER_SYSTEMNAME_XXX in der Umgebung XXX_PLATZHALTER_UMGEBUNG_XXX.
rem #
rem # Erstellungsdatum:           XXX_PLATZHALTER_ERSTELLDATUM_XXX
rem # Erstellt durch:             XXX_PLATZHALTER_ERSTELLER_XXX
rem #
rem # Datum der letzten Änderung: XXX_PLATZHALTER_ÄNDERUNGSDATUM_XXX
rem # Änderung durch:             XXX_PLATZHALTER_ÄNDERER_XXX
rem #
rem # Version: $Id: update-db-schema.bat 159308 2014-08-19 13:11:09Z sdm_fdoerr $
rem ########################################################################

sqlplus -S /nolog @00_update-main.sql 01_environment.sql  logs/ausgabe.log

findstr /r "SP2-[0-9][0-9][0-9][0-9]: ORA-[0-9][0-9][0-9][0-9][0-9]:" logs\ausgabe.log
