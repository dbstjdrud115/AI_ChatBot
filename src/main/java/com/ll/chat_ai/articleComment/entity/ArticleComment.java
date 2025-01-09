package com.ll.chat_ai.articleComment.entity;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.common.entity.baseEntity;
import com.ll.chat_ai.member.entity.Member;
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
public class ArticleComment extends baseEntity {
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;
}
