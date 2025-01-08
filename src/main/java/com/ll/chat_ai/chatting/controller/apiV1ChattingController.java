package com.ll.chat_ai.chatting.controller;


import com.ll.chat_ai.chatting.dto.chattingDTO;
import com.ll.chat_ai.chatting.entity.chattingEntity;
import com.ll.chat_ai.chatting.entity.chattingRoomEntity;
import com.ll.chat_ai.chatting.service.chattingService;
import com.ll.chat_ai.common.CreateMessage;
import com.ll.chat_ai.common.CreateRoom;
import com.ll.chat_ai.common.rsData.resultData;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@lombok.extern.slf4j.Slf4j
@Controller
@RestController
@RequestMapping("/api/v1/chat")
@AllArgsConstructor
@CrossOrigin(
        origins = {"https://cdpn.io", "http://localhost:5173"}
)
@Slf4j
public class apiV1ChattingController {

    private final OpenAiChatModel openAiChatModel;
    private final chattingService chattingService;

    //챗봇 집어넣기. OpenAI 의존성 주입 및 GPT사용을 위한 KEY. 그리고 선결제 필요하다고 한다.
    @GetMapping("/aiCall")
    public Map<String, String> test(@RequestBody String msg){
        Map<String, String> response = new HashMap<>();
        String openAiResponse = openAiChatModel.call(msg);
        response.put("openAi 회신결과", openAiResponse);
        System.out.println(openAiResponse);
        return response;
    }


    //채팅방 목록조회
    @GetMapping("/rooms")
    public resultData getChattingRoomList(){
        List<chattingRoomEntity> chattingRoomList = chattingService.getChattingList();
        return resultData.of("200", "조회결과", chattingRoomList);
    }

    //채팅방 한 건 조회
    @GetMapping("/rooms/{id}")
    public resultData getOneChattingRoom(@PathVariable long id){
        Optional<chattingRoomEntity> getOneChattingRoom = chattingService.getOneChattingRoom(id);
        return resultData.of("200", "조회결과", getOneChattingRoom);
    }


    //해당 채팅방의 메시지 가져오기
    @GetMapping("/rooms/{id}/messages")
    public resultData getMessageInRoom(@PathVariable Long id, @RequestParam(value = "afterChatMessageId", required = false)Long messageId){
        List<chattingEntity> chattingList = chattingService.getMessagesFromRoomId(id, messageId);
        return resultData.of("200", "채팅방 메시지 회신", chattingList);
    }


    /* 채팅방 생성
       messageDTO를 따로 만들기 싫어서, chattingDTO에 다 때려박아넣었다.
       문제는 호출마다 valid체크를 서로 달리해야 하기에, 그룹단위로 묶을 수 있도록
        CreateMessage, createRoom이란 Interface를 만들고, 그룹단위로 valid를 처리하게 하였다.
     */
    @PostMapping("/rooms")
    public resultData<chattingDTO> createRoom(@Validated(CreateRoom.class) @RequestBody chattingDTO req){
        try {
            chattingService.createRoom(req);
            return resultData.of("200", "채팅방 생성 성공", req);
        } catch (Exception e) {
            log.error("에러발생", e);
            return resultData.of("400", "채팅방 생성 실패", req);
        }
    }


    //채팅방 내에서 메시지 입력
    @PostMapping("/rooms/{chatRoomId}/message")
    public resultData createMessage(@PathVariable long chatRoomId, @Validated(CreateMessage.class) @RequestBody chattingDTO req){
        chattingEntity chatMsg = chattingService.createMessage(chatRoomId,req);
        if(chatMsg != null) {
            return resultData.of("200", "메시지 입력성공", chatMsg);
        }else{
            return resultData.of("400", "메시지 입력실패");
        }
    }



}
