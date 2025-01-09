package com.ll.chat_ai.articleTag.service;

import com.ll.chat_ai.articleTag.entity.ArticleTag;
import com.ll.chat_ai.articleTag.repository.ArticleTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagService {

    private ArticleTagRepository articleTagRepository;

    public List<ArticleTag> findByAuthorId(Long id) {
        return articleTagRepository.findByArticle_authorId(id);
    }

    public List<ArticleTag> findByAuthorUsername(String user) {
        return articleTagRepository.findByArticle_authorUsername(user);
    }
}
