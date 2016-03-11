drop table if exists Teilnehmer cascade constraints;

drop table if exists TeilnehmerZeitraum cascade constraints;

drop table if exists Tag cascade constraints;

drop table if exists Terminfindung cascade constraints;

drop table if exists Zeitraum cascade constraints;
    
create table Teilnehmer (
        teilnehmer_nr number(19,0) not null auto_increment,
        name varchar2(255 char),
        terminfnd_nr number(19,0),
        primary key (teilnehmer_nr)
    );

    create table TeilnehmerZeitraum (
        teilnehmerZeitraum_nr number(19,0) not null auto_increment,
        praeferenz number(3,0) not null,
        teilnehmer_nr number(19,0),
        zeitraum_nr number(19,0),
        primary key (teilnehmerZeitraum_nr)
    );

    create table Tag (
        tag_nr number(19,0) not null auto_increment,
        datum date,
        terminfnd_nr number(19,0),
        primary key (tag_nr)
    );

    create table Terminfindung (
        terminfnd_nr number(19,0) not null auto_increment,
        organisator_name varchar2(255 char),
        veranst_name varchar2(255 char),
        zeitraum_nr number(19,0),
        primary key (terminfnd_nr)
    );

    create table Zeitraum (
        zeitraum_nr number(19,0) not null auto_increment,
        beschreibung varchar2(255 char),
        tag_nr number(19,0),
        primary key (zeitraum_nr)
    );

    alter table Teilnehmer 
        add constraint FK550A971DB6E73F16 
        foreign key (terminfnd_nr)
        references Terminfindung;

    alter table TeilnehmerZeitraum 
        add constraint FKF2081DDA6FFDCF69 
        foreign key (teilnehmer_nr) 
        references Teilnehmer;

    alter table TeilnehmerZeitraum 
        add constraint FKF2081DDAE4F25CE9 
        foreign key (zeitraum_nr)
        references Zeitraum;

    alter table Tag
        add constraint FK951BD2B1B6E73F16 
        foreign key (terminfnd_nr)
        references Terminfindung;

    alter table Terminfindung 
        add constraint FKDD29BCC4E4F25CE9 
        foreign key (zeitraum_nr)
        references Zeitraum;

    alter table Zeitraum 
        add constraint FK1DC3F3BDD18C39E9 
        foreign key (tag_nr)
        references Tag;
