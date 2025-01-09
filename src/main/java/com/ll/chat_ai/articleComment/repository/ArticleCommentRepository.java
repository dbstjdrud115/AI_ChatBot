package com.ll.chat_ai.articleComment.repository;

import com.ll.chat_ai.articleComment.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

    List<ArticleComment> findByArticleId(Long articleId);
    List<ArticleComment> findByAuthorId(Long id);

    //@Query("SELECT c FROM chattingEntity c WHERE c.chattingRoomEntity.id = :roomId AND c.id > :messageId")
    //List<ArticleComment> findByAuthorId();
    //List<chattingEntity> findByRoomIdAndIdGreaterThan(@Param("messageId") Long messageId, @Param("roomId") Long roomId);
}
