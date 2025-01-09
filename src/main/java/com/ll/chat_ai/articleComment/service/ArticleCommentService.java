package com.ll.chat_ai.articleComment.service;

import com.ll.chat_ai.articleComment.entity.ArticleComment;
import com.ll.chat_ai.articleComment.repository.ArticleCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {
    final ArticleCommentRepository articleCommentRepository;

    public List<ArticleComment> findByAuthorId(Long id) {
        return articleCommentRepository.findByAuthorId(id);
    }
}