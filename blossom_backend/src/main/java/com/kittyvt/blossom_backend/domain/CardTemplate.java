package com.kittyvt.blossom_backend.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CardTemplate {
    private Integer dmg;
    private Integer hp;
    private String image;
    private String name;
    private Ability ability;
    private Type type;

    @JsonCreator
    public CardTemplate(
            @JsonProperty("dmg") Integer dmg,
            @JsonProperty("hp") Integer hp,
            @JsonProperty("image") String image,
            @JsonProperty("name") String name,
            @JsonProperty("ability") Ability ability,
            @JsonProperty("type") Type type) {
        this.dmg = dmg;
        this.hp = hp;
        this.image = image;
        this.name = name;
        this.ability = ability;
        this.type = type;
    }
}
