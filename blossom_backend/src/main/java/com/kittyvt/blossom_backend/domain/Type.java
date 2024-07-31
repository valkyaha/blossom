package com.kittyvt.blossom_backend.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Type {
    private Long id;
    private String type;

    @JsonCreator
    public Type(
            @JsonProperty("id") Long id,
            @JsonProperty("type") String type) {
        this.id = id;
        this.type = type;
    }
}
