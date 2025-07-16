create table users
(
    id          bigserial not null,
    first_name  varchar   not null,
    last_name   varchar   not null,
    birth_date  date      not null,
    gender      varchar   not null,
    interests   varchar   not null,
    city        varchar   not null,
    password    varchar   not null,
    primary key (id),
    constraint users_first_name_last_name_unique unique (first_name, last_name)
);

ALTER SEQUENCE users_id_seq RESTART WITH 1;
