package com.ll.chat_ai.article.article.service;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.article.service.ArticleService;
import com.ll.chat_ai.articleComment.entity.ArticleComment;
import com.ll.chat_ai.articleComment.service.ArticleCommentService;
import com.ll.chat_ai.articleTag.entity.ArticleTag;
import com.ll.chat_ai.articleTag.service.ArticleTagService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
//@ActiveProfiles({"test", "dev"})
@Transactional
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleTagService articleTagService;

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

        assertThat(author.getAuthor()).isEqualTo("user1");
    }

    //테스트는 성공했다고 하는데,
    //정작 update가 안됨.
    /*
    *  이후 테스트에서 데이터의 실제 변경여부를 확인하고 싶다면.
    *
    * 1. ActiveProfile 에 dev를 포함시키기.  test는 memorydb를 쓰고 있다.
    * 2. @Test는 Rollback되는 대상임으로 rollback을 아래와 같이 false로 설정한다.
    *
    * */
    @DisplayName("1번 글의 제목을 수정한다.")
    //@Rollback(value = false)
    @Test
    void t4() {
        Article article = articleService.findById(1L).get();
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


    /*수정과 삭제는 Setter, remove,add처럼
    * 엔티티와 직접적으로 연관된 Jparepository.api를 사용하지 않지만,
    * set, remove된 엔티티의 변화를 @Transcation에서 감지하여,
    * 트랜젝션 종료시 엔티티의 값을 실제 변경해준다.(더티 체킹)
    * */
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

    @DisplayName("게시물 별 댓글 수 출력")
    @Test
    void t8() {
        List<Article> articles = articleService.findAll();
        articles.forEach(article -> {
            System.out.println("게시물 번호: " + article.getId());
            System.out.println("댓글 수: " + article.getComments().size());
        });
    }


    @DisplayName("1번 게시물의 태그(String)를 반환한다.")
    @Test
    void t9() {
        Article article1 = articleService.findById(1L).get();
        String tagsStr = article1.getTagsStr();
        assertThat(tagsStr).isEqualTo("#자바 #백엔드");
    }

    /*10~13번 테스트의 경우 코드가 많이 꼬여있어서 차분히 정리해야한다. */
    @DisplayName("1번 게시물 toString")
    @Test
    //ToString은 모든 필드를 출력하는 문자열을 반환하는데,
    //Article클래스가 Member, AritcleComment와 양방향 관계를 맺다보니,
    //참조형 무한반복의 에러가 생긴다.
    void t10() {
        Article article1 = articleService.findById(1L).get();
        System.out.println(article1);
    }

    @DisplayName("1번 회원이 작성한 댓글들")
    @Test
    void t11() {
        List<ArticleComment> articleComments = articleCommentService.findByAuthorId(1L);
        assertThat(articleComments.size()).isGreaterThan(0);
    }

    @DisplayName("1번 회원이 작성한 태그들")
    @Test
    void t12() {
        List<ArticleTag> articleTags = articleTagService.findByAuthorId(1L);

        assertThat(articleTags.size()).isGreaterThan(0);
    }

    @DisplayName("아이디가 user1 인 회원이 작성한 태그들")
    @Test
    void t13() {
        List<ArticleTag> articleTags = articleTagService.findByAuthorUsername("user1");
        assertThat(articleTags.size()).isGreaterThan(0);
    }
}
