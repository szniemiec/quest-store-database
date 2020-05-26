create table "Roles"
(
    id   serial not null
        constraint roles_pk
            primary key,
    name text
);

alter table "Roles"
    owner to kjghikddzjahoq;

create unique index roles_id_uindex
    on "Roles" (id);

create table modules
(
    id   serial not null
        constraint modules_pk
            primary key,
    name text
);

alter table modules
    owner to kjghikddzjahoq;

create unique index modules_id_uindex
    on modules (id);

create table "Quest_status"
(
    id   serial not null
        constraint quest_status_pk
            primary key,
    name text
);

alter table "Quest_status"
    owner to kjghikddzjahoq;

create unique index quest_status_id_uindex
    on "Quest_status" (id);

create table "Artifact_status"
(
    id   serial not null
        constraint artifact_status_pk
            primary key,
    name text
);

alter table "Artifact_status"
    owner to kjghikddzjahoq;

create unique index artifact_status_id_uindex
    on "Artifact_status" (id);

create table "Users"
(
    id         serial not null
        constraint users_pk
            primary key,
    login      text   not null,
    password   text   not null,
    email      text,
    role_id    integer,
    first_name text,
    last_name  text,
    module_id  integer,
    coins      integer
);

alter table "Users"
    owner to kjghikddzjahoq;

create unique index users_id_uindex
    on "Users" (id);

create table "Quest_categories"
(
    id   serial not null
        constraint quest_categories_pk
            primary key,
    name text
);

alter table "Quest_categories"
    owner to kjghikddzjahoq;

create unique index quest_categories_id_uindex
    on "Quest_categories" (id);

