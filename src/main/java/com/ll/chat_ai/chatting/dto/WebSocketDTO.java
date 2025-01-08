package com.ll.chat_ai.chatting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketDTO {

    private long roomId;
    private String content;
    private String author;

}
