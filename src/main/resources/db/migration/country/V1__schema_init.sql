create extension if not exists "uuid-ossp";

create table if not exists "country"
(
    id            UUID unique        not null default   uuid_generate_v1() primary key,
    name          varchar(50)        unique not null,
    code          varchar(50)        not null
    );

alter table "country"
    owner to postgres;

truncate "country";

insert into "country"(name, code)
values ('Russia', 'RU');
insert into "country"(name, code)
values ('Kazakhstan', 'KZ');
insert into "country"(name, code)
values ('Germany', 'DE');
insert into "country"(name, code)
values ('United States', 'US');