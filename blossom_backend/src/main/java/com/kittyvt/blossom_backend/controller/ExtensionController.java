package com.kittyvt.blossom_backend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
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
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cards")
public class ExtensionController {

    Logger logger = LoggerFactory.getLogger(ExtensionController.class);

    public ExtensionController(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    private final WebSocketHandler webSocketHandler;

    @CrossOrigin(origins = "*")
    @PutMapping()
    public ResponseEntity<Object> putCards(
            @RequestBody CardTemplate cardTemplate,
            @RequestHeader("Authorization") String token) {

        if (isValidToken(token))
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.BAD_REQUEST)
                    .setMessage("Missing Token")
                    .build();

        JWT jwt = new JWT();
        Map<String, Claim> jwtToken = jwt.decodeJwt(splitToken(token)).getClaims();

        String channelId = jwtToken.get("channel_id").asString();
        String userId = jwtToken.get("user_id").asString();

        WebSocketSession session = getWebSocketSession(channelId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(cardTemplate.toString()));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        webSocketHandler.sendMessageToChannel(channelId, cardTemplate);

        return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.OK)
                .setObjectResponse(cardTemplate)
                .setMessage("Card added successfully")
                .build();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/abilities")
    public ResponseEntity<Object> getCardsAbility(@RequestHeader("Authorization") String token) {

        if (isValidToken(token)) return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setMessage("Missing Token")
                .build();

        JWT jwt = new JWT();
        Map<String, Claim> jwtToken = jwt.decodeJwt(splitToken(token)).getClaims();
        String channelId = jwtToken.get("channel_id").asString();
        String userId = jwtToken.get("user_id").asString();

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

    @GetMapping("/type")
    public ResponseEntity<Object> getCardsType(@RequestHeader("Authorization") String token) {

        if (isValidToken(token)) return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setMessage("Missing Token")
                .build();

        JWT jwt = new JWT();
        Map<String, Claim> jwtToken = jwt.decodeJwt(splitToken(token)).getClaims();
        String channelId = jwtToken.get("channel_id").asString();
        String userId = jwtToken.get("user_id").asString();

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

    @GetMapping("/all")
    public ResponseEntity<Object> getCards(@RequestHeader("Authorization") String token) {

        if (isValidToken(token)) return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setMessage("Missing Token")
                .build();

        JWT jwt = new JWT();
        Map<String, Claim> jwtToken = jwt.decodeJwt(splitToken(token)).getClaims();
        String channelId = jwtToken.get("channel_id").asString();
        String userId = jwtToken.get("user_id").asString();

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

    @GetMapping()
    public ResponseEntity<Object> getCard(@RequestHeader("Authorization") String token) {

        if (isValidToken(token)) return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setMessage("Missing Token")
                .build();

        JWT jwt = new JWT();
        Map<String, Claim> jwtToken = jwt.decodeJwt(splitToken(token)).getClaims();
        String channelId = jwtToken.get("channel_id").asString();
        String userId = jwtToken.get("user_id").asString();

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

    private WebSocketSession getWebSocketSession(String channelId) {
        return null;
    }

    private static boolean isValidToken(String token) {
        return token == null || token.isEmpty();
    }

    private String splitToken(String token) {
        return token.split(" ")[1];
    }
}
