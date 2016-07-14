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
-- Beispielskript für die Tabellenerzeugung.
--
-- Erstellungsdatum:           13.07.16
-- Erstellt durch:             Björn Saxe, msg systems
--
-- Datum der letzten Änderung: 
-- Änderung durch:             
--
-- Version: $Id: 04-2_tabellen_erzeugen.sql 159308 2014-08-19 13:11:09Z sdm_fdoerr $
-- -----------------------------------------------------------------------------------------------------

PURGE recyclebin;

create table terminfindung.Tag (
        id number(19,0) not null,
        datum timestamp,
        terminfindung_id number(19,0),
        primary key (id)
    );

create table terminfindung.Teilnehmer (
        id number(19,0) not null,
        name varchar2(255 char),
        terminfindung_id number(19,0),
        primary key (id)
    );
    
create table terminfindung.TeilnehmerZeitraum (
        id number(19,0) not null,
        praeferenz number(10,0),
        teilnehmer_id number(19,0),
        zeitraum_id number(19,0),
        primary key (id)
    );

create table terminfindung.Terminfindung (
        id number(19,0) not null,
        createDate timestamp,
        organisator_name varchar2(255 char),
        updateDate timestamp,
        veranstaltungName varchar2(255 char),
        zeitraum_nr number(19,0),
        primary key (id)
    );

create table terminfindung.Zeitraum (
        id number(19,0) not null,
        beschreibung varchar2(255 char),
        tag_id number(19,0),
        primary key (id)
    );
    
create table terminfindung.batchstatus (
        batchId varchar2(255 char) not null,
        batchName varchar2(255 char),
        batchStatus varchar2(255 char),
        satzNummerLetztesCommit number(19,0),
        schluesselLetztesCommit varchar2(255 char),
        datumLetzterStart timestamp,
        datumLetzterAbbruch timestamp,
        datumLetzterErfolg timestamp,
        primary key (batchId)
    );
    
alter table terminfindung.Tag 
        add constraint FK1477A4659C8A2 
        foreign key (terminfindung_id) 
        references terminfindung.Terminfindung;
        
alter table terminfindung.Teilnehmer 
        add constraint FK550A971D4659C8A2 
        foreign key (terminfindung_id) 
        references terminfindung.Terminfindung;
        
alter table terminfindung.TeilnehmerZeitraum 
        add constraint FKF2081DDAC74EFE32 
        foreign key (zeitraum_id) 
        references terminfindung.Zeitraum;
        
alter table terminfindung.Terminfindung 
        add constraint FKDD29BCC4C74EFEDB 
        foreign key (zeitraum_nr) 
        references terminfindung.Zeitraum;
        
alter table terminfindung.Zeitraum 
        add constraint FK1DC3F3BD91EA66E2 
        foreign key (tag_id) 
        references terminfindung.Tag;
        
create sequence terminfindung.hibernate_sequence;