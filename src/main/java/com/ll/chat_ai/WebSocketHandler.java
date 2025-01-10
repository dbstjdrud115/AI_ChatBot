package com.ll.chat_ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.chat_ai.chatting.dto.WebSocketDTO;
import com.ll.chat_ai.chatting.dto.chattingDTO;
import com.ll.chat_ai.chatting.service.chattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableWebSocket
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private final chattingService ChattingService;
    //SSE마냥 연결된 대상들을 일괄적으로 관리하는 맵을 만든다.
    private final Map<String, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        //url에서 roomId 추출
        URI uri = session.getUri();
        String roomId = extractRoomId(session);

        //ws로 연결시도한 모든 Session관리. 마치
        //emitters라는 데이터콜렉션으로 emitter를 관리하던것을 떠오르게 한다.
        chatRooms.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet())
                .add(session);


        //쿼리스트링을 Json으로 추출
        String query = session.getUri().getQuery();
        JSONObject queryParams = convertQueryStringToJson(query);
        String afterChatMessageIdForString = queryParams.optString("afterChatMessageId", null);

        //Service에 써먹을 수 있는 long id로 형변환
        Long roomIdForLong = Long.parseLong(roomId);
        Long afterChatMessageId = Long.parseLong(afterChatMessageIdForString);


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



    //storm의 subscribe가 write에서 수행되듯이.
    //사용자가 입력시 화면의 wsRef.current.send에 따라,
    //handleTextMessage로 넘어오는것으로 보인다.
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String roomId = extractRoomId(session);
        ObjectMapper objectMapper = new ObjectMapper();
        //메시지 정보 저장.
        WebSocketDTO webSocketDTO = objectMapper.readValue(message.getPayload(), WebSocketDTO.class);

        chattingDTO req = chattingDTO.builder()
                .content(webSocketDTO.getContent())
                .writerName(webSocketDTO.getAuthor())
                .createdDate(LocalDateTime.now())  // 현재 시간
                .modifiedDate(LocalDateTime.now())  // 현재 시간
                .build();

        ChattingService.createMessage(webSocketDTO.getRoomId(),req);

        //모든 연결된 ws Session에 데이터 회신.
        Set<WebSocketSession> roomSessions = chatRooms.get(roomId);
        if (roomSessions != null) {
            for (WebSocketSession clientSession : roomSessions) {
                if (clientSession.isOpen()) {
                    clientSession.sendMessage(message);
                }
            }
        }

    }

    private String getRoomIdFromSession(WebSocketSession session) {
        // session.getUri()를 통해 roomId 추출 (URL에서 파라미터를 파싱)
        String uri = session.getUri().toString();
        String[] parts = uri.split("/");
        return parts[parts.length - 1]; // 마지막 부분이 roomId라고 가정
    }


    private String extractRoomId(WebSocketSession session) {
        String path = session.getUri().getPath();
        // "/ws/chat/{roomId}/messages" 형식에서 roomId 추출
        return path.split("/")[3];
    }

    //연결 끊기면 세션 제거
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String roomId = extractRoomId(session);
        // 세션 제거
        Set<WebSocketSession> roomSessions = chatRooms.get(roomId);
        if (roomSessions != null) {
            roomSessions.remove(session);
            // 방이 비었다면 방 제거
            if (roomSessions.isEmpty()) {
                chatRooms.remove(roomId);
            }
        }
        log.info("WebSocket connection closed. RoomId: {}, Session ID: {}", roomId, session.getId());
    }
}
