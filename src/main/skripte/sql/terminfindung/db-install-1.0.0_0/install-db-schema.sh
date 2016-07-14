########################################################################
# #%L
# plis-persistence
# %%
# 
# %%
# See the NOTICE file distributed with this work for additional
# information regarding copyright ownership.
# The Federal Office of Administration (Bundesverwaltungsamt, BVA)
# licenses this file to you under the Apache License, Version 2.0 (the
# License). You may not use this file except in compliance with the
# License. You may obtain a copy of the License at
# 
#     http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
# implied. See the License for the specific language governing
# permissions and limitations under the License.
# #L%
#
# Richtet die Datenbank für das System XXX_PLATZHALTER_SYSTEMNAME_XXX ein.
#
# Erstellungsdatum:           XXX_PLATZHALTER_ERSTELLDATUM_XXX
# Erstellt durch:             XXX_PLATZHALTER_ERSTELLER_XXX
#
# Datum der letzten Änderung: XXX_PLATZHALTER_ÄNDERUNGSDATUM_XXX
# Änderung durch:             XXX_PLATZHALTER_ÄNDERER_XXX
#
# Version: $Id: install-db-schema.sh 159250 2014-08-19 06:31:06Z sdm_fdoerr $
#######################################################################

#!/bin/bash
export ORACLE_SID=XXX

LogFile=logs/ausgabe.log

sqlplus -S /nolog @00_install-main.sql XXX_PLATZHALTER_UMGEBUNGSSKRIPT_XXX ${LogFile}
echo ''

AnzahlFehler=$(grep -c -E "SP2\-[0-9]{4}\:|ORA\-[0-9]{5}\:" ${LogFile})
echo "${AnzahlFehler}"
if [ ${AnzahlFehler} -ne 0 ] ; then
    echo "+++ ${AnzahlFehler} Fehler beim Ausführen des Skripts."
    grep -E "SP2\-[0-9]{4}\:|ORA\-[0-9]{5}\:" ${LogFile}
    echo "+++ Bitte die Log-Datei ${LogFile} überprüfen."
    exit 1
fi

exit
