CREATE TABLE ability
(
    id           integer primary key,
    ability_name text,
    effect       text,
    damage       integer,
    buff         integer
);

CREATE TABLE type
(
    id   integer primary key,
    type text
);

CREATE TABLE cards
(
    id      integer primary key,
    type    integer,
    image   blob,
    name    text,
    damage  integer,
    health  integer,
    ability integer,
    FOREIGN KEY (type) REFERENCES type (id)
);

CREATE TABLE users
(
    id         integer primary key,
    username   text,
    role       text,
    created_at integer,
    points     integer,
    card_id    text,
    FOREIGN KEY (card_id) REFERENCES cards (id)
);
