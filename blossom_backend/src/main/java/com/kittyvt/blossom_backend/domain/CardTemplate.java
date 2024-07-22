package com.kittyvt.blossom_backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardTemplate {
    private Integer dmg;
    private Integer hp;
    private String image;
    private String name;
    private Ability ability;
    private Type type;
}