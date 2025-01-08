package com.ll.chat_ai.chatting.repository;

import com.ll.chat_ai.chatting.entity.chattingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface chattingRepository extends JpaRepository<chattingEntity, Long> {

    /*채팅메시지(chattingEntity) 값을 가져오되,
      채팅방(chattingRommeEntity)의 roomId를 조건으로 사용함
      Join이 명시되어있지는 않지만, 다대일 종속관계를 설정했던것을 베이스로
      JPA가 알아서 Join을 걸어준다고 한다. 왜 mybatis를 대체하는지 이유를 알것같다.
     */
    @Query("SELECT c FROM chattingEntity c WHERE c.chattingRoomEntity.id = :roomId AND c.id > :messageId")
    List<chattingEntity> findByRoomIdAndIdGreaterThan(@Param("messageId") Long messageId, @Param("roomId") Long roomId);

}
