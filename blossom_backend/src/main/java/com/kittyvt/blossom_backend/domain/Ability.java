package com.kittyvt.blossom_backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ability {

    private Long id;
    @JsonProperty("ability_name")
    private String abilityName;
    private String effect;
    private Integer damage;
    private Integer buff;

}
