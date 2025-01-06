package com.ll.chat_ai.chatting.entity;

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
    private chattingRoomEntity chattingRoomEntity;
    private String writerName;
    private String content;

}