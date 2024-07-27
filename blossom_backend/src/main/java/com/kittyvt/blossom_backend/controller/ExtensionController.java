package com.kittyvt.blossom_backend.controller;

import com.kittyvt.blossom_backend.domain.Ability;
import com.kittyvt.blossom_backend.domain.CardTemplate;
import com.kittyvt.blossom_backend.domain.Type;
import com.kittyvt.blossom_backend.utils.ResponseEntityBuilderResponse;
import com.kittyvt.blossom_backend.websocket.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExtensionController {

    Logger logger = LoggerFactory.getLogger(ExtensionController.class);

    public ExtensionController(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    private final WebSocketHandler webSocketHandler;

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/cards/{channelId}")
    public ResponseEntity<Object> putCards(@RequestBody CardTemplate cardTemplate, @PathVariable String channelId, @RequestHeader("Authorization") String token) {

        if (token == null || token.isEmpty()) {
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.BAD_REQUEST)
                    .setMessage("Missing Token")
                    .build();
        }

        logger.info(token);

        //JWT jwt = new JWT();
        //Map<String, Claim> test = jwt.decodeJwt(token).getClaims();

        //Claim test2 = test.get("user_id");
        //test2.asString();

        webSocketHandler.sendMessageToChannel(channelId, cardTemplate);

        return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.OK)
                .setObjectResponse(cardTemplate)
                .setMessage("Card added successfully")
                .build();
    }

    @GetMapping("/cards/{channelId}/abilities")
    public ResponseEntity<Object> getCardsAbility(@PathVariable String channelId) {
        List<Ability> abilities = new ArrayList<>();
        abilities.add(new Ability(1L, "Pitudo", "Tengo un dildo en forma de pito furry", 1, 1));
        abilities.add(new Ability(2L, "Pitolargo", "Es mentira", 1, 1));
        abilities.add(new Ability(3L, "Pitocorto", "Sisoy", 1, 1));

        return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.OK)
                .setObjectResponse(abilities)
                .setMessage("Test")
                .build();
    }

    @GetMapping("/cards/{channelId}/type")
    public ResponseEntity<Object> getCardsType(@PathVariable String channelId) {
        List<Type> types = new ArrayList<>();
        types.add(new Type(1L, "BOSS"));
        types.add(new Type(1L, "AIR"));
        types.add(new Type(1L, "Frier"));
        types.add(new Type(1L, "HEHEHE"));

        return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.OK)
                .setObjectResponse(types)
                .setMessage("Test")
                .build();
    }

    @GetMapping("/cards/{channelId}")
    public ResponseEntity<Object> getCards(@PathVariable String channelId, @RequestHeader("Authorization") String token) {
        System.out.println(token);

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
