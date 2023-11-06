package io.zethange;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{chatId}")
@ApplicationScoped
public class StartWebSocket {
    private final Map<String, List<Session>> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("chatId") String chatId) {
        System.out.println("onOpen> " + chatId);
        sessions.computeIfAbsent(chatId, key -> new ArrayList<>()).add(session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("chatId") String chatId) {
        System.out.println("onClose> " + chatId);
        closeSession(session, chatId);
    }

    @OnError
    public void onError(Session session, @PathParam("chatId") String chatId, Throwable throwable) {
        System.out.println("onError> " + chatId + ": " + throwable);
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("chatId") String chatId, String message) {
        System.out.println("onMessage> " + chatId + ": " + message);
        sendMessage(session, chatId, message);
    }


    private void closeSession(Session session, String chatId) {
        final List<Session> chatSessions = sessions.get(chatId);
        final Iterator<Session> sessionIterator = chatSessions.iterator();
        while (sessionIterator.hasNext()) {
            final Session chatSession = sessionIterator.next();
            if (session.getId().equals(chatSession.getId())) {
                sessionIterator.remove();
                break;
            }
        }
    }

    private void sendMessage(Session session, String chatId, String message) {
        final List<Session> chatSessions = sessions.get(chatId);
        for (Session chatSession : chatSessions) {
            if (session.getId().equals(chatSession.getId())) {
                continue;
            }
            chatSession.getAsyncRemote().sendObject(message);
        }
    }
}
