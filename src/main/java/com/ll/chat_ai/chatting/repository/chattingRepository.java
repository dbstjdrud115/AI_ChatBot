package com.ll.chat_ai.chatting.repository;

import com.ll.chat_ai.chatting.entity.chattingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface chattingRepository extends JpaRepository<chattingEntity, Long> {

}
