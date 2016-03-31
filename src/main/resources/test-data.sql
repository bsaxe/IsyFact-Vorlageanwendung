---
-- #%L
-- Terminfindung
-- %%
-- Copyright (C) 2015 - 2016 Bundesverwaltungsamt (BVA), msg systems ag
-- %%
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- 
--      http://www.apache.org/licenses/LICENSE-2.0
-- 
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
-- #L%
---
insert into Terminfindung values(2,'Test','Test',null);
insert into Teilnehmer values(1200,'Seppl',2);
insert into Tag values(4,DATE '2015-04-15',2);
insert into Zeitraum values(5,'abends',4);
insert into TeilnehmerZeitraum values(6, 0, 1200, 5);
