package com.ll.chat_ai;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(WebSocketHandler(), "/ws/chat/{roomId}")
                .setAllowedOrigins("http://localhost:5173"); // React 앱의 URL 허용
    }

    // WebSocket 핸들러 정의
    public WebSocketHandler WebSocketHandler() {
        return new WebSocketHandler();
    }
}


/*
package com.ll.chat_ai;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //registry.addEndpoint("/ws").withSockJS();
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:5173").withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    */
/*//*
/WebSocket에서 사용하는 방식.
        messagingTemplate.convertAndSend("/topic/chat/writeMessage", new writeMessageResponse(ch));



    // 서버설정
    기존 registry.addEndpoint("/ws").withSockJS();
    현재 registry.addEndpoint("/ws").withSockJS();

    // 프론트 설정
    기존 const socket = new SockJS("/ws");
    기존 const stompClient = Stomp.over(socket);

    현재 const ws = new WebSocket(`ws://localhost:8070/ws`)

    //연결시 req url
    기존 코드 : ws://localhost:8090/ws/917/lx3w2wrw/websocket
    현재 작업 : ws://localhost:8070/ws


    <!--SSE와 비슷하게 WS 연결용 상태를 설정한다. -->
            stompClient.connect({}, function (frame) {
        console.log("Connected: " + frame);
        stompClient.subscribe(`/topic/chat/writeMessage`, function (data) {
            console.log(data.body)
            Chat__loadMore();
        });



        *//*

    }*/
