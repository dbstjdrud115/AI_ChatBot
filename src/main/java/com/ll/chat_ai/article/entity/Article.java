package com.ll.chat_ai.article.entity;

import com.ll.chat_ai.articleComment.entity.ArticleComment;
import com.ll.chat_ai.common.entity.baseEntity;
import com.ll.chat_ai.member.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends baseEntity {


    private String title;
    private String body;

    @ManyToOne
    private Member member;

    //article entity와 다대일관계를 맺고 있으며, 연관 article이 사라지면 전체 삭제
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<ArticleComment> comments = new ArrayList<>();


    public void addComment(Member memberAuthor, String commentBody) {

        ArticleComment articleComment = ArticleComment.builder()
                .article(this)
                .author(memberAuthor)
                .body(commentBody)
                .build();
        comments.add(articleComment);
    }

    public void removeComment(ArticleComment comment) {
        comments.remove(comment);
    }

}
