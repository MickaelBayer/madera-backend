create table component_family
(
    id   serial not null
        constraint component_family_pk
            primary key,
    name text
);

alter table component_family
    owner to admin;

create table component
(
    id               integer not null
        constraint range_pk
            primary key,
    name             text,
    unit_use         text,
    nature           integer,
    default_quantity double precision,
    family           integer
        constraint component_component_family_id_fk
            references component_family
);

comment on table component is 'composants';

alter table component
    owner to admin;


create unique index range_family_id_uindex
    on component_family (id);


create table customer
(
    id        serial not null
        constraint customer_pk
            primary key,
    address   text,
    firstname text   not null,
    lastname  text,
    mail      text   not null,
    phone     text
);

alter table customer
    owner to admin;

create unique index customer_id_uindex
    on customer (id);

create unique index customer_mail_uindex
    on customer (mail);


create table provider
(
    id      serial not null
        constraint provider_pk
            primary key,
    name    text,
    address text,
    mail    text
);

alter table provider
    owner to admin;

create unique index provider_id_uindex
    on provider (id);



create table equipment
(
    id             serial not null
        constraint equipment_pk
            primary key,
    name           text,
    coupe_principe text,
    provider       integer
        constraint equipment_provider_id_fk
            references provider
);

comment on table equipment is 'matériaux';

alter table equipment
    owner to admin;

create unique index equipment_id_uindex
    on equipment (id);


create table ranges
(
    id                     serial not null
        constraint ranges_pk
            primary key,
    name                   text,
    type                   text,
    percentage_final_price double precision
);

comment on table ranges is 'gammes';

alter table ranges
    owner to admin;

create unique index range_id_uindex
    on ranges (id);

create table module
(
    id             serial           not null
        constraint module_pk
            primary key,
    starting_price double precision not null,
    nature         text,
    angle          text,
    unit_use       text,
    cctp           text,
    info           text,
    name           text,
    range          integer
        constraint module_ranges_id_fk
            references ranges
);

alter table module
    owner to admin;

create unique index module_id_uindex
    on module (id);


create table module_component
(
    id        serial not null
        constraint module_component_pk
            primary key,
    component integer
        constraint module_component_component_id_fk
            references component,
    module    integer
        constraint module_component_module_id_fk
            references module
);

alter table module_component
    owner to admin;

create unique index module_component_id_uindex
    on module_component (id);


create table module_equipment
(
    id        serial not null
        constraint module_equipment_pk
            primary key,
    module    integer
        constraint module_equipment_module_id_fk
            references module,
    equipment integer
        constraint module_equipment_equipment_id_fk
            references equipment
);

alter table module_equipment
    owner to admin;

create unique index module_equipment_id_uindex
    on module_equipment (id);

create table role
(
    id   serial not null
        constraint role_pk
            primary key,
    name text   not null
);

alter table role
    owner to admin;

create unique index role_id_uindex
    on role (id);

create unique index role_name_uindex
    on role (name);


create table "user"
(
    id        serial not null
        constraint user_pk
            primary key,
    mail      text   not null,
    firstname text   not null,
    lastname  text   not null,
    password  text   not null,
    role      integer
        constraint user_role_id_fk
            references role,
    phone     text
);

alter table "user"
    owner to admin;

create unique index user_id_uindex
    on "user" (id);

create unique index user_mail_uindex
    on "user" (mail);



create table project
(
    id       serial  not null
        constraint project_pk
            primary key,
    name     text    not null,
    "user"   integer
        constraint project_user_id_fk
            references "user",
    customer integer not null
        constraint project_customer_id_fk
            references customer
);

alter table project
    owner to admin;

create unique index project_id_uindex
    on project (id);


create table project_module
(
    id      serial not null
        constraint project_module_pk
            primary key,
    name    text,
    width   double precision,
    length  double precision,
    price   double precision,
    module  integer
        constraint project_module_module_id_fk
            references module,
    project integer
        constraint project_module_project_id_fk
            references project
);

alter table project_module
    owner to admin;

create unique index project_module_id_uindex
    on project_module (id);



create table quotation_state
(
    id   serial not null
        constraint quotation_state_pk
            primary key,
    name text   not null
);

alter table quotation_state
    owner to admin;

create unique index quotation_state_id_uindex
    on quotation_state (id);



create table quotation
(
    id          serial                                 not null
        constraint quotation_pk
            primary key,
    created_at  timestamp with time zone default now() not null,
    updated_at  timestamp with time zone default now() not null,
    total_price double precision                       not null,
    state       integer
        constraint quotation_quotation_state_id_fk
            references quotation_state,
    discount    integer,
    project     integer
        constraint quotation_project_id_fk
            references project
);

alter table quotation
    owner to admin;

create unique index quotation_id_uindex
    on quotation (id);


CREATE OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
NEW.updated_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER set_timestamp
BEFORE UPDATE ON quotation
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();
