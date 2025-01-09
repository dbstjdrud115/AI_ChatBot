package com.ll.chat_ai.article.entity;

import com.ll.chat_ai.articleComment.entity.ArticleComment;
import com.ll.chat_ai.articleTag.entity.ArticleTag;
import com.ll.chat_ai.common.entity.baseEntity;
import com.ll.chat_ai.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)

public class Article extends baseEntity {
    private String title;
    private String content;
    //일대다 관계는, 즉시 가져오는값이라도 그 수가 한 둘에 국한되기에,
    // 기본설정이 (fetch=FetchType.EAGER)임.
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;


    /*
      article entity와 다대일관계를 맺고 있으며, 연관 article이 사라지면 전체 삭제
      Article을 호출하면, 종속관계인 comments를 따로 불러와야 된다.

      기본적인 상태를, (fetch=FetchType.LAZY)라고 한다.
      근데 따로 따로 불러오려니 DB 연결이 늘어나는 부담이 생기는데 이것을 N+1문제라고 한다.

       orphanRemoval = 자식요소 삭제시 본인삭제
    */
    @OneToMany(mappedBy = "article", cascade = ALL, orphanRemoval = true) // fetch = FetchType.LAZY
    @Builder.Default
    @ToString.Exclude
    private List<ArticleComment> comments = new ArrayList<>();

    /*jpa.save() 메서드가 수행되지 않았는데,
     * comment가 추가되는 이유는, JPA에서
     * Entity에서 발생한 set 을 감지해서
     * 더티체킹 기능을 통해, 데이터를 수정해준다.
     * */
    public void addComment(Member memberAuthor, String commentBody) {
        ArticleComment comment = ArticleComment.builder()
                .article(this)
                .author(memberAuthor)
                .body(commentBody)
                .build();

        comments.add(comment);
    }

    public void removeComment(ArticleComment articleComment) {
        comments.remove(articleComment);
    }

    @OneToMany(mappedBy = "article", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<ArticleTag> tags = new ArrayList<>();

    public void addTag(String tagContent) {
        ArticleTag tag = ArticleTag.builder()
                .article(this)
                .content(tagContent)
                .build();
        tags.add(tag);
    }

    public void addTag(String... tagContents) {
        for (String tagContent : tagContents) {
            addTag(tagContent);
        }
    }


    public String getTagsStr() {
        String tagsStr = tags
                .stream()
                .map(ArticleTag::getContent)
                .collect(Collectors.joining(" #"));

        if (tagsStr.isBlank()) {
            return "";
        }

        return "#" + tagsStr;

    }
}