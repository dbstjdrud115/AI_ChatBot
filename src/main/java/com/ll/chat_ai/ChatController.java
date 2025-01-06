package com.ll.chat_ai;


import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/v1/chat")
@RestController
public class ChatController {

    private final OpenAiChatModel openAiChatModel;

    public ChatController(OpenAiChatModel openAiChatModel){
        this.openAiChatModel = openAiChatModel;
    }

    @GetMapping("/ai")
    public Map<String, String> test(@RequestBody String msg){
        Map<String, String> response = new HashMap<>();
        String openAiResponse = openAiChatModel.call(msg);
        response.put("openAi 회신결과", openAiResponse);
        System.out.println(openAiResponse);
        return response;
    }
}
