package com.kittyvt.blossom_backend.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler extends TextWebSocketHandler {

    Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    private final Map<String, WebSocketSession> sessionsByChannel = new ConcurrentHashMap<>();
    private final Map<String, CompletableFuture<String>> pendingRequests = new ConcurrentHashMap<>();

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        if (message instanceof TextMessage) {
            String receivedMessage = ((TextMessage) message).getPayload();
            logger.info("Received message: {}", receivedMessage);

            if (receivedMessage.contains("id")) {
                String channelId = getChannelIdFromSession(session);

                CompletableFuture<String> future = pendingRequests.remove(channelId);
                if (future != null) {
                    future.complete(receivedMessage);
                }
            } else {
                logger.error("Unexpected message type: {}", message.getClass().getName());
            }
        } else {
            logger.error("Unexpected message type: {}", message.getClass().getName());
        }
    }

    public CompletableFuture<String> sendMessageToChannelAndWait(String channelId, String message) {
        CompletableFuture<String> future = new CompletableFuture<>();
        pendingRequests.put(channelId, future);

        WebSocketSession session = sessionsByChannel.get(channelId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                logger.error("Error sending message: {}", e.getMessage());
                future.completeExceptionally(e);
            }
        } else {
            future.completeExceptionally(new IllegalStateException("No open session for channel: " + channelId));
        }

        return future;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String channelId = getChannelIdFromSession(session);
        sessionsByChannel.put(channelId, session);
        logger.info("Connection established: {} for channel: {}", session.getId(), channelId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String channelId = getChannelIdFromSession(session);
        sessionsByChannel.remove(channelId);
        logger.info("Connection closed: {} for channel: {}", session.getId(), channelId);
    }

    public void sendMessageToChannel(String channelId, Object messageObject) {
        WebSocketSession session = sessionsByChannel.get(channelId);
        if (session != null && session.isOpen()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String message = mapper.writeValueAsString(messageObject);
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                logger.error("Error sending message: {}", e.getMessage());
            }
        }
    }

    private String getChannelIdFromSession(WebSocketSession session) {
        try {
            String uri = Objects.requireNonNull(session.getUri()).toString();
            return uri.substring(uri.lastIndexOf("/") + 1);
        } catch (NullPointerException e) {
            logger.error("Error extracting channel ID from session: {}", e.getMessage());
        }

        return null;
    }
}
