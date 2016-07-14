-- -----------------------------------------------------------------------------------------------------
--
-- #%L
-- plis-persistence
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
-- Dieses Skript erzeugt die benötigte Benutzer und Rechte für das System XXX_PLATZHALTER_SYSTEMNAME_XXX.
--
-- Erstellungsdatum:            13.07.16
-- Erstellt durch:              Björn Saxe, msg systems
-- 
-- Datum der letzten Änderung:  
-- Änderung durch:              
--
-- Version: $Id: 03_user.sql 159308 2014-08-19 13:11:09Z sdm_fdoerr $
-- -----------------------------------------------------------------------------------------------------

CREATE USER &USERNAME IDENTIFIED BY &PASSWORD DEFAULT TABLESPACE &TABLESPACE_NAME QUOTA &TABLESPACE_QUOTA ON &TABLESPACE_NAME;

-- Explizit CREATE SESSION anstatt CONNECT-Rolle
GRANT CREATE SESSION TO &USERNAME;

-- Temporär CREATE-Rechte setzen fuer initiales Anlegen.
GRANT CREATE TABLE TO &USERNAME;
GRANT CREATE SEQUENCE TO &USERNAME;
GRANT CREATE PROCEDURE TO &USERNAME;
GRANT CREATE TRIGGER TO &USERNAME;
GRANT CREATE VIEW TO &USERNAME;

-- Eventuell weitere Rechte (Queuing, Materialized View, ...)
