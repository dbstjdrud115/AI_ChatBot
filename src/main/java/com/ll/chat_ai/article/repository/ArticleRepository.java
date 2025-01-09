package com.ll.chat_ai.article.repository;

import com.ll.chat_ai.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}