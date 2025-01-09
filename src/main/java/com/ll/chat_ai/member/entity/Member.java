package com.ll.chat_ai.member.entity;

import com.ll.chat_ai.common.entity.baseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends baseEntity {

    private String author;
    private String number;

}
