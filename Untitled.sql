CREATE TABLE "ability" (
  "id" integer PRIMARY KEY,
  "ability_name" varchar,
  "effect" varchar,
  "damage" integer,
  "buff" integer
);

CREATE TABLE "Type" (
  "type" varchar PRIMARY KEY
);

CREATE TABLE "cards" (
  "id" integer PRIMARY KEY,
  "type" Type,
  "image" blob,
  "name" varchar,
  "damage" integer,
  "health" integer,
  "ability" integer
);

CREATE TABLE "users" (
  "id" integer PRIMARY KEY,
  "username" varchar,
  "role" varchar,
  "created_at" timestamp,
  "points" double,
  "card_id" Card.Card.ca
);

ALTER TABLE "cards" ADD FOREIGN KEY ("type") REFERENCES "Type" ("type");

ALTER TABLE "users" ADD FOREIGN KEY ("card_id") REFERENCES "cards" ("id");

ALTER TABLE "ability" ADD FOREIGN KEY ("id") REFERENCES "cards" ("ability");
