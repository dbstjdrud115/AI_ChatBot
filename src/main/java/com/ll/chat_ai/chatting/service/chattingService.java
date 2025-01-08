package com.ll.chat_ai.chatting.service;

import com.ll.chat_ai.chatting.dto.chattingDTO;
import com.ll.chat_ai.chatting.entity.chattingEntity;
import com.ll.chat_ai.chatting.entity.chattingRoomEntity;
import com.ll.chat_ai.chatting.repository.chattingRepository;
import com.ll.chat_ai.chatting.repository.chattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public List<chattingRoomEntity> getChattingRoomList() {
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

    public void createRoom(chattingDTO req) {
        chattingRoomEntity chatRoom = chattingRoomEntity.builder()
                .name(req.getName())
                .build();
        chattingRoomRepository.save(chatRoom);
    }


    public List<chattingEntity> getMessagesFromRoomId(Long id, Long messageId) {
        Optional<chattingRoomEntity> chatRoomOptional = chattingRoomRepository.findById(id);
        if (chatRoomOptional.isPresent()) {
            return chattingRepository.findByRoomIdAndIdGreaterThan(messageId, id);
        } else {
            return new ArrayList<>();
        }
    }

    public chattingEntity createMessage(long roomId, chattingDTO req) {

        //1. 방 있는지 체크 chatRoomOptional
        //2. 방 있으면 메시지 Entity에 값 세팅.
        //3. 세팅값으로 저장
        Optional<chattingRoomEntity> chatRoomOptional = chattingRoomRepository.findById(roomId);

        if (chatRoomOptional.isPresent()) {
            chattingEntity chatMsg = chattingEntity.builder()
                    .chattingRoomEntity(chatRoomOptional.get())
                    .writerName(req.getWriterName())
                    .content(req.getContent())
                    .build();
            chattingRepository.save(chatMsg);
            return chatMsg;
        } else {
            return null;
        }
    }
}
