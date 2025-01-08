package com.ll.chat_ai.article.service;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.article.repository.ArticleRepository;
import com.ll.chat_ai.common.rsData.resultData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public resultData<Article> write(int i, String subject, String content) {

        Article article = Article.builder().subject(subject).content(content).build();
        articleRepository.save(article);
        return resultData.of("200", "%s 라는 내용을 작성하였습니다.".formatted(content),  article);

    }
}
