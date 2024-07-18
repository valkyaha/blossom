# BBDD Specs

```
Table ability {
  id integer [primary key]
  ability_name varchar
  effect varchar
  damage integer
  buff integer
}

Table Type {
  type varchar [primary key]
}

Table cards {
  id integer [primary key]
  type Type
  image blob
  name varchar
  damage integer
  health integer
  ability integer
}

Table users {
  id integer [primary key]
  username varchar
  role varchar
  created_at timestamp
  points double
  card_id Card.ca 
}


Ref: cards.type > Type.type
Ref: cards.id - users.card_id
Ref: cards.ability < ability.id 
```