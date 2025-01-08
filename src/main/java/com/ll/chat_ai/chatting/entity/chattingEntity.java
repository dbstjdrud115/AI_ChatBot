package com.ll.chat_ai.chatting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.chat_ai.common.entity.baseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;


@Entity
@Setter
@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@ToString(callSuper = true)
public class chattingEntity extends baseEntity {

    //채팅방의존. 작성자와 작성내용
    @ManyToOne
    /*Json 변환 및 데이터 반환 비표시할떄 쓴다.
      종속관계 설정을 위해 Room정보를 필드에 삽입하다보니,
    이 설정을 안걸면 Room정보까지 포함하여 회신된다.
     다만, DTO를 분리해서, 필요한 정보만 회신될 수 있도록 하는게
     일반적이라고는 한다. 이건 그냥 일하러 가는 곳마다 CASE BY CASE인 걸로..
     */
    @JsonIgnore
    private chattingRoomEntity chattingRoomEntity;
    private String writerName;
    private String content;

}