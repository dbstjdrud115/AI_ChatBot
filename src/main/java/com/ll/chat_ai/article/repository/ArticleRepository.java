package com.ll.chat_ai.article.repository;

import com.ll.chat_ai.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

    List<Article> findByOrderByIdDesc();
    //Spring Data JPA방식. 이름규칙으로 JpaRepo외의 API도 만들수있지만, 명명문제가 생김.
    //Page<Article> findByAuthor_usernameContainingOrTitleContainingOrContentContaining(String kw, String kw_, String kw__, Pageable pageable);
    //Page<Article> findByAuthor_usernameContainingOrTitleContainingOrContentContainingOrTags_contentOrComments_author_usernameContainingOrComments_bodyContaining(String kw, String kw_, String kw__, String kw___, String kw____, String kw_____, Pageable pageable);

}
