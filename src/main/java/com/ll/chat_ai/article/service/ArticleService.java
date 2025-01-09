package com.ll.chat_ai.article.service;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.article.repository.ArticleRepository;
import com.ll.chat_ai.articleComment.entity.ArticleComment;
import com.ll.chat_ai.common.rsData.resultData;
import com.ll.chat_ai.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@org.springframework.transaction.annotation.Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public resultData<Article> write(Long memberId, String title, String body) {


        Article article = Article.builder()
                                 .member(Member.builder().id(memberId).build())
                                 .title(title)
                                 .body(body)
                                 .build();

        articleRepository.save(article);
        return resultData.of("200", "%s 라는 내용을 작성하였습니다.".formatted(body),  article);
    }

    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public void modify(Article article, String modifiedTitle, String modifiedBody) {
        article.setTitle(modifiedTitle);
        article.setBody(modifiedBody);

        /*
        * 클래스 전체로 readOnly를 걸어두고,
        * 개별 cud에 transaction을 건 다음,
        * Entity.set을 하면 변화를 감지하고, 알아서
        *
        *
        * */
        //트랜젝션 걸려있는경우에서 set을 하면, 값 변화를 감지하고
        //jpa에서 알아서 데이터 작업을해준다는것 같다.
      //  articleRepository.save(article);
    }

    public void modifyComment(ArticleComment comment, String body) {
        comment.setBody(body);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}
