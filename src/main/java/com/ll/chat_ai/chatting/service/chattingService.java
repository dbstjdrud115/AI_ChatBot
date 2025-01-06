package com.ll.chat_ai.chatting.service;

import com.ll.chat_ai.chatting.entity.chattingEntity;
import com.ll.chat_ai.chatting.entity.chattingRoomEntity;
import com.ll.chat_ai.chatting.repository.chattingRepository;
import com.ll.chat_ai.chatting.repository.chattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class chattingService {

    private final chattingRepository chattingRepository;
    private final chattingRoomRepository chattingRoomRepository;

    //초기데이터-채팅방 생성
    public chattingRoomEntity makeInitData(String name){
        chattingRoomEntity initResult = chattingRoomEntity.builder().name(name).build();
        chattingRoomRepository.save(initResult);
        return initResult;
    }


    public List<chattingRoomEntity> getChattingList() {
        return chattingRoomRepository.findAll();
    }

    public Optional<chattingRoomEntity> getOneChattingRoom(long id) {
        Optional<chattingRoomEntity> getOneChattingRoom = chattingRoomRepository.findById(id);
        return getOneChattingRoom;
    }

    public void makeInitMessage(chattingRoomEntity chattingRoomEntity1, String writerName, String content) {

        chattingEntity ChattingEntity = chattingEntity.builder()
                .chattingRoomEntity(chattingRoomEntity1)
                .writerName(writerName)
                .content(content)
                .build();

        chattingRepository.save(ChattingEntity);
    }

    /*    public ArticleDTO getOneArticle(Long id) {
        *//*
         * 전체 조회인 getArticleList은 Optional을 사용하지 않는데,
         * 단건 조회인 getOneArticle은  Optional을 사용하는 이유는,
         * 전자의 반환값인 List는 값이 없더라도 빈 데이터콜렉션을 반환하지만,
         * 후자는 값이 없을경우 Optional.empty()를 반환하기 떄문이다.
         *  따라서, 값이 없는 경우에 대한 조치사안을 작성해두어야 한다.
         * *//*
        Optional<ArticleEntity> getOneArticle = articleRepository.findById(id);
        return getOneArticle.map(result->new ArticleDTO(result)).orElse(null);
    }*/


}
