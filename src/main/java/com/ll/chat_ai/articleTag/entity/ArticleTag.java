package com.ll.chat_ai.articleTag.entity;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.common.entity.baseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ArticleTag extends baseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    private String content;
}


