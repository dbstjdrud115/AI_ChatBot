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
public class Member extends baseEntity {
    String username;
    String password;
}
