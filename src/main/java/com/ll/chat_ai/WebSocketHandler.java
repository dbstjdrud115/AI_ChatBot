package com.ll.chat_ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.chat_ai.chatting.dto.WebSocketDTO;
import com.ll.chat_ai.chatting.dto.chattingDTO;
import com.ll.chat_ai.chatting.entity.chattingEntity;
import com.ll.chat_ai.chatting.service.chattingService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.*;

@Component
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private final chattingService ChattingService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //첫 연결시 수행. 메시지 정보 message를 회신해줘야 한다.
        //방 정보 뿐 아니라, 특정 메시지 이후라는것도 포함해야 한다.
        //정의서에 따르면 ROOMID뿐 아니라 afterChatMessageId 는 쿼리스트링으로..

        //url에서 roomId 추출
        URI uri = session.getUri();
        String path = uri.getPath();
        String roomIdForString = extractRoomId(path);

        //쿼리스트링 Json으로 추출
        String query = session.getUri().getQuery();
        JSONObject queryParams = convertQueryStringToJson(query);
        String afterChatMessageIdForString = queryParams.optString("afterChatMessageId", null);

        //Service에 맞게 long으로 형변환
        Long roomId = Long.parseLong(roomIdForString);
        Long afterChatMessageId = Long.parseLong(afterChatMessageIdForString);

        /*List<chattingEntity> messages = ChattingService.getMessagesFromRoomId(roomId, afterChatMessageId);
        session.sendMessage(new TextMessage(messages.toString()));
        연결에 대한 response 데이터는 일단 보류하고,
        채팅방 상세 접속시 메세지 데이터 조회하는것부터 처리해야할듯하다.
        */
    }

    private JSONObject convertQueryStringToJson(String query) {
        JSONObject jsonObject = new JSONObject(); // JSON 객체 생성

        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&"); // "&"로 분리하여 각 파라미터 처리
            for (String pair : pairs) {
                String[] keyValue = pair.split("="); // "="로 분리하여 키와 값 얻기
                if (keyValue.length > 1) {
                    jsonObject.put(keyValue[0], keyValue[1]); // JSON 객체에 추가
                } else {
                    jsonObject.put(keyValue[0], ""); // 값이 없으면 빈 문자열 추가
                }
            }
        }
        return jsonObject;
    }

    private String extractRoomId(String path) {
        //url의 슬래시를 기준으로 잘라 동적변수 자리에 해당하는 값을 빼낸다.
        String[] segments = path.split("/");
        return segments[segments.length - 2];
    }

    //storm의 subscribe가 write에서 수행되듯이.
    //사용자가 입력시 handleTextMessage가 실행된다.
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
        chattingEntity savedChatMessage = ChattingService.createMessage(webSocketDTO.getRoomId(),req);

        //화면에서 받을 데이터가 필요하다.

        //1. message.id >> 만들어진 메시지 id
        //2. message.isMyMessage >> 화면에서 어차피 보내줌
        //3.

        //

        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("id", UUID.randomUUID().toString()); // 고유 ID 생성
        responseMessage.put("roomId", webSocketDTO.getRoomId());
        responseMessage.put("content", webSocketDTO.getContent());
        responseMessage.put("author", webSocketDTO.getAuthor());
        responseMessage.put("createdAt", new Date().toString()); // 현재 시간
        String jsonResponse = objectMapper.writeValueAsString(responseMessage);

        // TextMessage 는 CharSequence를 취급하는데,
        // String, chatAt등의 문자 관련 데이터타입은 CharSequence의 구현체이다.
        session.sendMessage(new TextMessage(jsonResponse));
    }

    private String getRoomIdFromSession(WebSocketSession session) {
        // session.getUri()를 통해 roomId 추출 (URL에서 파라미터를 파싱)

        //1월9일 12시 32분 기준 ws://localhost:8070/ws/chat/1 이렇게 오고있다.
        //
        String uri = session.getUri().toString();
        String[] parts = uri.split("/");

        // roomId는 URL의 마지막 부분에 위치한다고 가정
        return parts[parts.length - 1]; // 마지막 부분이 roomId라고 가정
    }
}
