package com.ll.chat_ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.chat_ai.chatting.dto.WebSocketDTO;
import com.ll.chat_ai.chatting.dto.chattingDTO;
import com.ll.chat_ai.chatting.entity.chattingEntity;
import com.ll.chat_ai.chatting.service.chattingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 받은 메시지 처리
        System.out.println("Received message: " + message.getPayload());
        System.out.println("연결성공 handleTextMessage");
        session.sendMessage(new TextMessage("Echo: " + message.getPayload()));
    }
}*/

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private chattingService chattingService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //첫 연결시 수행
        String roomId = getRoomIdFromSession(session);
        System.out.println("Connected to room: " + roomId);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        WebSocketDTO webSocketDTO = objectMapper.readValue(message.getPayload(), WebSocketDTO.class);

        /*private long roomId;  채팅방 id
        private String content; 입력내용
        private String author;  입력자
        */

        chattingDTO req = chattingDTO.builder()
                .content(webSocketDTO.getContent())
                .writerName(webSocketDTO.getAuthor())
                .build();

        //save가 채팅방에 종속되어 잘 일어나는지 확인해야함.
        //docker 켜두고 로그 봐가면서 할것. >> 저장이 어떤 쿼리로 이뤄지는지.
        chattingEntity savedChatMessage = chattingService.createMessage(webSocketDTO.getRoomId(),req);
        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("id", UUID.randomUUID().toString()); // 고유 ID 생성
        responseMessage.put("roomId", webSocketDTO.getRoomId());
        responseMessage.put("content", webSocketDTO.getContent());
        responseMessage.put("author", webSocketDTO.getAuthor());
        responseMessage.put("createdAt", new Date().toString()); // 현재 시간


        String jsonResponse = objectMapper.writeValueAsString(responseMessage);

        // TextMessage 는 CharSequence를 취급하는데, String, chatAt등의 문자 관련
        // 데이터타입은 CharSequence의 구현체이다.
        session.sendMessage(new TextMessage(jsonResponse));
    }

    private String getRoomIdFromSession(WebSocketSession session) {
        // session.getUri()를 통해 roomId 추출 (URL에서 파라미터를 파싱)
        String uri = session.getUri().toString();
        String[] parts = uri.split("/");

        // roomId는 URL의 마지막 부분에 위치한다고 가정
        return parts[parts.length - 1]; // 마지막 부분이 roomId라고 가정
    }
}
