package com.kittyvt.blossom_backend.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kittyvt.blossom_backend.domain.CardTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WebSocketHandler extends TextWebSocketHandler {

    Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    private final Map<String, WebSocketSession> sessionsByChannel = new HashMap<>();

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        String receivedMessage = (String) message.getPayload();
        session.sendMessage(new TextMessage("Received: " + receivedMessage));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String channelId = getChannelIdFromSession(session);
        sessionsByChannel.put(channelId, session);
        logger.info("Connection established: {} para el canal: {}", session.getId(), channelId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String channelId = getChannelIdFromSession(session);
        sessionsByChannel.remove(channelId);
        logger.info("Connection closed: {} para el canal: {}", session.getId(), channelId);
    }

    public void sendMessageToChannel(String channelId, CardTemplate cardTemplate) {
        WebSocketSession session = sessionsByChannel.get(channelId);
        if (session != null && session.isOpen()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String message = mapper.writeValueAsString(cardTemplate);
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private String getChannelIdFromSession(WebSocketSession session) {
        try {
            String uri = Objects.requireNonNull(session.getUri()).toString();
            return uri.substring(uri.lastIndexOf("/") + 1);
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        }

        return null;
    }
}
