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
rem # Richtet die Datenbank für das System XXX_PLATZHALTER_SYSTEMNAME_XXX ein.
rem #
rem # Erstellungsdatum:           13.07.16
rem # Erstellt durch:             Björn Saxe, msg systems
rem #
rem # Datum der letzten Änderung: 
rem # Änderung durch:             
rem #
rem # Version: $Id: install-db-schema.bat 159308 2014-08-19 13:11:09Z sdm_fdoerr $
rem ########################################################################

sqlplus -S /nolog @00_install-main.sql 01_environment.sql logs/ausgabe.log

findstr /r "SP2-[0-9][0-9][0-9][0-9]: ORA-[0-9][0-9][0-9][0-9][0-9]:" logs\ausgabe.log

