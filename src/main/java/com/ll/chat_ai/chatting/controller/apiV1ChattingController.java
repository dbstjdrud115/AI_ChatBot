package com.ll.chat_ai.chatting.controller;


import com.ll.chat_ai.chatting.dto.chattingDTO;
import com.ll.chat_ai.chatting.entity.chattingRoomEntity;
import com.ll.chat_ai.chatting.service.chattingService;
import com.ll.chat_ai.common.CreateMessage;
import com.ll.chat_ai.common.CreateRoom;
import com.ll.chat_ai.common.rsData.resultData;
import lombok.AllArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/v1/chat")
@AllArgsConstructor
@CrossOrigin(
        origins = "https://cdpn.io"
)
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

    @GetMapping("/rooms")
    public resultData getChattingRoomList(){
        List<chattingRoomEntity> chattingRoomList = chattingService.getChattingList();
        return resultData.of("200", "조회결과", chattingRoomList);
    }

    @GetMapping("/rooms/{id}")
    public resultData getOneChattingRoom(@PathVariable long id){
        Optional<chattingRoomEntity> getOneChattingRoom = chattingService.getOneChattingRoom(id);
        return resultData.of("200", "조회결과", getOneChattingRoom);
    }

    @GetMapping("/rooms/{id}/messages")
    public String getMessageInRoom(@PathVariable Long id, @RequestParam(value = "afterChatMessageId", required = false)Long messageId){

        return "채팅방에 포함된 메세지 정보 조회";
    }


    /*messageDTO를 따로 만들기 싫어서, chattingDTO에 다 때려박아넣었다.
       문제는 호출마다 valid체크를 서로 달리해야 하기에, 그룹단위로 묶을 수 있도록
        CreateMessage, createRoom이란 Interface를 만들고, 그룹단위로 valid를 처리하게 하였다.
     */
    @PostMapping("/rooms/{id}")
    public String createRoom(@PathVariable long id, @Validated(CreateRoom.class) @RequestBody chattingDTO req){
        return "채팅방 생성";
    }


    //종속성과 제목이 필요함.
    @PostMapping("/rooms/{id}/message")
    public String createMessage(@PathVariable long id, @Validated(CreateMessage.class) @RequestBody chattingDTO req){
        return "채팅방 메시지 입력";
    }



}
