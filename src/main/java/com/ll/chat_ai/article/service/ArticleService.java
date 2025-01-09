package com.ll.chat_ai.article.service;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.article.repository.ArticleRepository;
import com.ll.chat_ai.articleComment.entity.ArticleComment;
import com.ll.chat_ai.common.rsData.resultData;
import com.ll.chat_ai.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@org.springframework.transaction.annotation.Transactional(readOnly = true)
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public resultData<Article> write(Long memberId, String title, String content) {
        Article article = Article.builder()
                .author(Member.builder().id(memberId).build())
                .title(title)
                .content(content)
                .build();

        articleRepository.save(article);

        return resultData.of("200", "글 작성 성공", article);
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public void modify(Article article, String title, String content) {
        article.setTitle(title);
        article.setContent(content);

//        articleRepository.save(article);
    }

    @Transactional
    public void modifyComment(ArticleComment comment, String commentBody) {
        comment.setBody(commentBody);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Page<Article> search(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
