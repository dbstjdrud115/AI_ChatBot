package com.ll.chat_ai.articleTag.service;

import com.ll.chat_ai.articleTag.entity.ArticleTag;
import com.ll.chat_ai.articleTag.repository.ArticleTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleTagService {
    private final ArticleTagRepository articleTagRepository;

    public List<ArticleTag> findByAuthorId(Long authorId) {
        return articleTagRepository.findByArticle_authorId(authorId);
    }

    public List<ArticleTag> findByAuthorUsername(String AuthorUsername) {
        return articleTagRepository.findByArticle_authorUsername(AuthorUsername);
    }
}
