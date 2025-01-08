package com.ll.chat_ai.article.entity;

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
public class Article extends baseEntity {

    private String subject;
    private String content;

}
