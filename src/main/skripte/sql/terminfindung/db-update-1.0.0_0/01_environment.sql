-- -----------------------------------------------------------------------------------------------------
--
-- #%L
-- isy-persistence
-- %%
-- 
-- %%
-- See the NOTICE file distributed with this work for additional
-- information regarding copyright ownership.
-- The Federal Office of Administration (Bundesverwaltungsamt, BVA)
-- licenses this file to you under the Apache License, Version 2.0 (the
-- License). You may not use this file except in compliance with the
-- License. You may obtain a copy of the License at
-- 
--     http://www.apache.org/licenses/LICENSE-2.0
-- 
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
-- implied. See the License for the specific language governing
-- permissions and limitations under the License.
-- #L%
--
-- Dieses Skript enthält alle Umgebungsvariablen des Systems Terminfindung.
-- 
-- Erstellungsdatum:           13.07.2016
-- Erstellt durch:             Björn Saxe, msg systems
-- 
-- Datum der letzten Änderung: 
-- Änderung durch:             
--
-- -----------------------------------------------------------------------------------------------------

-- -------------------------------------
-- Konfiguration: Datenbankverbindungen
-- -------------------------------------
-- Oracle-Datenbankverbindung (Connection-String) 
-- in der Form <user-id>/<pwd>@<IP-Adresse>:<port>/<db-instanz>
-- für den SYS-Admin.
DEFINE SYSADMIN_CONNECTION      = 'system/geheim@127.0.0.1:1521/xe';

-- Oracle-Datenbankverbindung (Connection-String) 
-- in der Form <user-id>/<pwd>@<IP-Adresse>:<port>/<db-instanz>
-- für den Benutzer des neu anzulegenden Schemas.
DEFINE USER_CONNECTION          = 'terminfindung/terminfindung@127.0.0.1:1521/xe';


-- -------------------------------------
-- Konfiguration: Tablespace
-- -------------------------------------
-- Name des Tablespace, in dem das Schema angelegt wird.
DEFINE TABLESPACE_NAME          = 'terminfindung_schema_version';

-- Name des Data-Files inklusiv vollständigen Pfad, 
-- das dem Tablespace zugeordnet wird.
DEFINE DATAFILE_NAME            = 'c:\temp\terminfindung_schema_version.ora';

-- Größe des Data-Files incl. Einheit (z.B. 20G für 20 GB).
DEFINE DATAFILE_SIZE            = '10M';

-- Größe des nächsten zu allokierenden Extends 
-- incl. Einheit (z.B. 5M für 5 MB).
DEFINE AUTOEXTEND_NEXT_SIZE     = '5M'; 

-- Maximale Größe des Data-Files incl. Einheit (z.B. 100G für 100GB).
DEFINE AUTOEXTEND_MAX_SIZE      = '10M';


-- -------------------------------------
-- Konfiguration: User
-- -------------------------------------
-- Name des Users für das Schema (ist gleich dem Schemanamen). 
-- Muss mit <user-id> im Parameter USER_CONNECTION übereinstimmen.
DEFINE USERNAME                 = 'terminfindung';

-- Kennwort des Users für das Schema. 
-- Muss mit <pwd> User im Parameter USER_CONNECTION übereinstimmen.
DEFINE PASSWORD                 = 'terminfindung';

-- Quota des User für das Schema.
DEFINE TABLESPACE_QUOTA         = '10M';
