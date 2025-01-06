package com.ll.chat_ai;


/*@RequestMapping("/api/v1/chat")
@RestController
public class ChatController {

    private final OpenAiChatModel openAiChatModel;

    public ChatController(OpenAiChatModel openAiChatModel){
        this.openAiChatModel = openAiChatModel;
    }

    //챗봇 집어넣기. OpenAI 의존성 주입 및 GPT사용을 위한 KEY. 그리고 선결제 필요하다고 한다.
    @GetMapping("/ai")
    public Map<String, String> test(@RequestBody String msg){
        Map<String, String> response = new HashMap<>();
        String openAiResponse = openAiChatModel.call(msg);
        response.put("openAi 회신결과", openAiResponse);
        System.out.println(openAiResponse);
        return response;
    }
}*/
