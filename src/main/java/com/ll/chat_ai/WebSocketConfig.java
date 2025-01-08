package com.ll.chat_ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        /*
        *  WebSocket은 연결시 요청의 쿼리스트링을 고려하지 않는다.
        *  그래서 ?afterMessageId부분이 paths에 기입되어있지 않더라도,
        *   url 부분만 고려하여 ws 연결을 성사시킨다.
        */
        registry.addHandler(webSocketHandler, "/ws/chat/{roomId}/messages")
                .setAllowedOrigins("http://localhost:5173"); // React 앱의 URL 허용
    }
}