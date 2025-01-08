package com.ll.chat_ai.articleComment.entity;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.common.entity.baseEntity;
import com.ll.chat_ai.member.entity.Member;
import jakarta.persistence.Entity;
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

    /*
    *  댓글은 게시물에 달리는 고로, 게시물에 의존한다.
    * 허나 한편으로는, 그 댓글을 올린 작성자에 의존한다.
    * */
    @ManyToOne
    private Article article;
    @ManyToOne
    private Member author;

    //댓글내용
    private String body;
}
