package com.ll.chat_ai.chatting.dto;

import com.ll.chat_ai.common.CreateMessage;
import com.ll.chat_ai.common.CreateRoom;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(force = true)//기본생성자를 만들면서도, final field를 적용시키기 위해 사용
public class chattingDTO {

    private final Long id;

    @NotBlank(groups = CreateRoom.class)
    private String name;
    @NotBlank(groups = CreateMessage.class)
    private String content;
    @NotBlank(groups = CreateMessage.class)
    private String writerName;

    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;


    //DTO와 Entity간 치환을 위해 설정
  /*  public chattingDTO(chattingEntity chattingEntity) {
        this.id = chattingEntity.getId();
        this.content = chattingEntity.getContent();
        this.writerName = chattingEntity.getWriterName();
        this.createdDate = chattingEntity.getCreateDate();
        this.modifiedDate = chattingEntity.getModifyDate();
    }*/
}
