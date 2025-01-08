package com.ll.chat_ai.chatting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.chat_ai.common.entity.baseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;


@Entity
@Setter
@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@ToString(callSuper = true)
public class chattingRoomEntity extends baseEntity {

    //채팅방 제목 및, 채팅메시지 리스트
    @OneToMany
    @JsonIgnore
    private List<chattingEntity> chattingEntityList;
    private String name;

}
