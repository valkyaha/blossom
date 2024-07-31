package com.kittyvt.blossom_backend.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Ability {

    private Long id;
    @JsonProperty("ability_name")
    private String abilityName;
    private String effect;
    private Integer damage;
    private Integer buff;

    @JsonCreator
    public Ability(
            @JsonProperty("id") Long id,
            @JsonProperty("ability_name") String abilityName,
            @JsonProperty("effect") String effect,
            @JsonProperty("damage") Integer damage,
            @JsonProperty("buff") Integer buff) {
        this.id = id;
        this.abilityName = abilityName;
        this.effect = effect;
        this.damage = damage;
        this.buff = buff;
    }
}
