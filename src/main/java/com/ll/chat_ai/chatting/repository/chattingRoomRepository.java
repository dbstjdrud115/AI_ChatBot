package com.ll.chat_ai.chatting.repository;

import com.ll.chat_ai.chatting.entity.chattingRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface chattingRoomRepository extends JpaRepository<chattingRoomEntity, Long> {

}
