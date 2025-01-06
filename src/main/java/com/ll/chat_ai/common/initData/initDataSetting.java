package com.ll.chat_ai.common.initData;

import com.ll.chat_ai.chatting.entity.chattingRoomEntity;
import com.ll.chat_ai.chatting.service.chattingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.stream.IntStream;

@Configuration
@Profile("!prod")
public class initDataSetting {

    @Autowired
    private Environment environment;

    @Bean
    public ApplicationRunner initNotProd(chattingService chattingService) {
        return args -> {

            String[] activeProfiles = environment.getActiveProfiles();

            chattingRoomEntity chattingRoomEntity1 =  chattingService.makeInitData("공부");
            chattingRoomEntity chattingRoomEntity2 =  chattingService.makeInitData("식사");
            chattingRoomEntity chattingRoomEntity3 =  chattingService.makeInitData("휴식");
            IntStream.rangeClosed(1, 5).forEach(num -> {
                String numAsString = String.valueOf(num);
                chattingService.makeInitMessage(chattingRoomEntity1, "사용자1", numAsString);
            });
            System.out.println("Not Prod");
        };
    }


}
