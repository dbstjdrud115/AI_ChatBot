package com.ll.chat_ai.article.article.service;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.article.service.ArticleService;
import com.ll.chat_ai.articleComment.entity.ArticleComment;
import com.ll.chat_ai.common.rsData.resultData;
import com.ll.chat_ai.member.entity.Member;
import com.ll.chat_ai.member.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MemberService memberService;

    @DisplayName("글 쓰기")
    @Test
    void t1() {

        resultData<Article> writeRs = articleService.write(1L, "제목", "내용");
        Article article = writeRs.getData();
        assertThat(article.getId()).isGreaterThan(0L);
    }

    @DisplayName("1번 글을 가져온다.")
    @Test
    void t2() {
        Article article = articleService.findById(1L).get();
        assertThat(article.getTitle()).isEqualTo("제목1");
    }

    @DisplayName("1번 글의 작성자의 username 은 user1 이다.")
    @Test
    void t3() {
        Article article = articleService.findById(1L).get();
        Member author = article.getMember();

        assertThat(author.getUsername()).isEqualTo("user1");
    }

    //왜 연결이 안되는지 문의해볼것.
    @DisplayName("1번 글의 제목을 수정한다.")
    @Test
    void t4() {
        Article article = articleService.findById(1L).get();
        //그냥 기입되어있음. Lock 테스트 등에 사용될 수 있을듯.
        articleService.modify(article, "수정된 제목", "수정된 내용");
        Article article_ = articleService.findById(1L).get();
        assertThat(article_.getTitle()).isEqualTo("수정된 제목");
    }

    @DisplayName("2번 글에 댓글들을 추가한다.")
    @Test
    @Rollback(false)
    void t5() {
        Member member1 = memberService.findById(1L).get();
        Article article2 = articleService.findById(2L).get();

        //멤버에 종속된 댓글!
        article2.addComment(member1, "댓글 입니다.");
    }


    @DisplayName("1번 글의 댓글들을 수정한다.")
    @Test
    void t6() {
        Article article = articleService.findById(2L).get();

        article.getComments().forEach(comment -> {
            articleService.modifyComment(comment, comment.getBody() + "!!");
        });
    }

    @DisplayName("1번 글의 댓글 중 마지막 것을 삭제한다.")
    @Test
    void t7() {
        Article article = articleService.findById(2L).get();

        ArticleComment lastComment = article.getComments().getLast();

        article.removeComment(lastComment);
    }

}
