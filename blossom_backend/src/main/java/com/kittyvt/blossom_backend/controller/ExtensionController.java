package com.kittyvt.blossom_backend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.concurrent.CompletableFuture;

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

        if (isToken(token))
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.BAD_REQUEST)
                    .setMessage("Missing Token")
                    .build();

        JWT jwt = new JWT();
        Map<String, Claim> jwtToken = jwt.decodeJwt(splitToken(token)).getClaims();

        String channelId = jwtToken.get("channel_id").asString();
        String userId = jwtToken.get("user_id").asString();
        Result result = new Result(channelId, userId);

        WebSocketSession session = getWebSocketSession(result.channelId(), result.userId());
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(cardTemplate.toString()));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        webSocketHandler.sendMessageToChannel(result.channelId(), cardTemplate);

        return new ResponseEntityBuilderResponse<>()
                .setStatus(HttpStatus.OK)
                .setObjectResponse(cardTemplate)
                .setMessage("Card added successfully")
                .build();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/abilities")
    public ResponseEntity<Object> getCardsAbility(@RequestHeader("Authorization") String token) {
        if (isValidToken(token)) {
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.BAD_REQUEST)
                    .setMessage("Missing Token")
                    .build();
        }

        JWT jwt = new JWT();
        Map<String, Claim> jwtToken = jwt.decodeJwt(splitToken(token)).getClaims();
        String channelId = jwtToken.get("channel_id").asString();

        try {
            CompletableFuture<String> futureResponse = webSocketHandler.sendMessageToChannelAndWait(channelId, "GET_ABILITY_" + channelId);
            String cardData = futureResponse.join();
            List<Ability> cardTemplate = new ObjectMapper().readValue(cardData, new TypeReference<List<Ability>>() {
            });

            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.OK)
                    .setObjectResponse(cardTemplate)
                    .setMessage("Card abilities retrieved successfully")
                    .build();
        } catch (Exception e) {
            logger.error("Error retrieving card ability: {}", e.getMessage());
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .setMessage("Error retrieving card ability")
                    .build();
        }
    }

    @GetMapping("/type")
    public ResponseEntity<Object> getCardsType(@RequestHeader("Authorization") String token) {
        if (isValidToken(token)) {
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.BAD_REQUEST)
                    .setMessage("Missing Token")
                    .build();
        }

        JWT jwt = new JWT();
        Map<String, Claim> jwtToken = jwt.decodeJwt(splitToken(token)).getClaims();
        String channelId = jwtToken.get("channel_id").asString();

        try {
            CompletableFuture<String> futureResponse = webSocketHandler.sendMessageToChannelAndWait(channelId, "GET_TYPE_" + channelId);
            String cardData = futureResponse.join();

            List<Type> cardTemplates = new ObjectMapper().readValue(cardData, new TypeReference<List<Type>>() {
            });

            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.OK)
                    .setObjectResponse(cardTemplates)
                    .setMessage("Card types retrieved successfully")
                    .build();
        } catch (Exception e) {
            logger.error("Error retrieving card type: {}", e.getMessage());
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .setMessage("Error retrieving card type")
                    .build();
        }
    }


    @GetMapping("/all")
    public ResponseEntity<Object> getCards(@RequestHeader("Authorization") String token) {

        try {

            if (isValidToken(token)) return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.BAD_REQUEST)
                    .setMessage("Missing Token")
                    .build();

            JWT jwt = new JWT();
            Map<String, Claim> jwtToken = jwt.decodeJwt(splitToken(token)).getClaims();
            String channelId = jwtToken.get("channel_id").asString();
            String userId = jwtToken.get("user_id").asString();


            if (!channelId.equals(userId)) {
                return new ResponseEntityBuilderResponse<>()
                        .setStatus(HttpStatus.BAD_REQUEST)
                        .setMessage("General Error: user not allowed")
                        .build();
            }

            CompletableFuture<String> futureResponse = webSocketHandler.sendMessageToChannelAndWait(channelId, "GET_ALL_CARDS");
            String cardData = futureResponse.join();
            List<CardTemplate> cards = new ObjectMapper().readValue(cardData, new TypeReference<List<CardTemplate>>() {
            });

            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.OK)
                    .setObjectResponse(cards)
                    .setMessage("Test")
                    .build();
        } catch (Exception e) {
            logger.error("Error retrieving card data: {}", e.getMessage());
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .setMessage("Error retrieving card data")
                    .build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<Object> getCard(@RequestHeader("Authorization") String token) {
        if (isValidToken(token)) {
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.BAD_REQUEST)
                    .setMessage("Missing Token")
                    .build();
        }

        JWT jwt = new JWT();
        Map<String, Claim> jwtToken = jwt.decodeJwt(splitToken(token)).getClaims();
        String channelId = jwtToken.get("channel_id").asString();
        String userId = jwtToken.get("user_id").asString();

        try {
            CompletableFuture<String> futureResponse = webSocketHandler.sendMessageToChannelAndWait(channelId, "GET_CARD_" + channelId + "_" + userId);
            String cardData = futureResponse.join();
            CardTemplate cardTemplate = new ObjectMapper().readValue(cardData, CardTemplate.class);

            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.OK)
                    .setObjectResponse(cardTemplate)
                    .setMessage("Card data retrieved successfully")
                    .build();
        } catch (Exception e) {
            logger.error("Error retrieving card data: {}", e.getMessage());
            return new ResponseEntityBuilderResponse<>()
                    .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .setMessage("Error retrieving card data")
                    .build();
        }
    }

    private WebSocketSession getWebSocketSession(String channelId, String userId) {

        return null;
    }

    private static boolean isValidToken(String token) {
        return token == null || token.isEmpty();
    }

    private String splitToken(String token) {
        return token.split("\\s")[1];
    }

    private record Result(String channelId, String userId) {
    }

    private static boolean isToken(String token) {
        return isValidToken(token);
    }
}
