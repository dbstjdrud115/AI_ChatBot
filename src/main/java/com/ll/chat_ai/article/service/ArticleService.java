package com.ll.chat_ai.article.service;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.article.repository.ArticleRepository;
import com.ll.chat_ai.articleComment.entity.ArticleComment;
import com.ll.chat_ai.common.rsData.resultData;
import com.ll.chat_ai.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        articleRepository.save(article);
    }

    public void modifyComment(ArticleComment comment, String body) {
        comment.setBody(body);
    }
}
