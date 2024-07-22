package com.kittyvt.blossom_backend.controller;

import com.kittyvt.blossom_backend.domain.Ability;
import com.kittyvt.blossom_backend.domain.CardTemplate;
import com.kittyvt.blossom_backend.domain.Type;
import com.kittyvt.blossom_backend.utils.ResponseEntityBuilderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExtensionController {

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/cards/{channelId}")
    public ResponseEntity<Object> putCards(@RequestBody CardTemplate cardTemplate, @PathVariable String channelId) {
        return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.OK)
                .setObjectResponse(cardTemplate)
                .setMessage("Test")
                .build();
    }

    @GetMapping("/cards/{channelId}/abilities")
    public ResponseEntity<Object> getCardsAbility(@PathVariable String channelId) {

        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Ability(1L, "TEST", "TEST", 1, 1));
        abilities.add(new Ability(1L, "TEST", "TEST", 1, 1));
        abilities.add(new Ability(1L, "TEST", "TEST", 1, 1));

        return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.OK)
                .setObjectResponse(abilities)
                .setMessage("Test")
                .build();
    }

    @GetMapping("/cards/{channelId}/type")
    public ResponseEntity<Object> getCardsType(@PathVariable String channelId) {

        List<Type> types = new ArrayList<>();
        types.add(new Type(1L, "TEST"));
        types.add(new Type(1L, "TEST"));
        types.add(new Type(1L, "TEST"));
        types.add(new Type(1L, "TEST"));

        return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.OK)
                .setObjectResponse(types)
                .setMessage("Test")
                .build();
    }

    @GetMapping("/cards/{channelId}")
    public ResponseEntity<Object> getCards(@PathVariable String channelId) {

        List<CardTemplate> cards = new ArrayList<>();
        cards.add(
                new CardTemplate(
                        1,
                        1,
                        "",
                        "TEST",
                        new Ability(1L, "TEST", "TEST", 1, 1),
                        new Type(1L, "TEST"
                        )
                )
        );

        cards.add(
                new CardTemplate(
                        1,
                        1,
                        "",
                        "TEST",
                        new Ability(1L, "TEST", "TEST", 1, 1),
                        new Type(1L, "TEST"
                        )
                )
        );

        return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.OK)
                .setObjectResponse(cards)
                .setMessage("Test")
                .build();
    }

    @GetMapping("/cards/{channelId}/{userId}")
    public ResponseEntity<Object> getCard(@PathVariable String channelId, @PathVariable String userId) {
        CardTemplate cardTemplate = new CardTemplate(
                10,
                20,
                "https://www.ikea.com/es/es/images/products/blahaj-peluche-tiburon__0710175_pe727378_s5.jpg?f=xl",
                "Shikanoko Noko",
                new Ability(1L,
                        "Shikanoko",
                        "Shikanoko noko koshi tan tan shikanoko noko koshi tan tan shikanoko noko koshi tan tan shikanoko noko koshi tan tan",
                        1,
                        1),
                new Type(1L, "5")
        );

        return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.OK)
                .setObjectResponse(cardTemplate)
                .setMessage("Test")
                .build();
    }
}
