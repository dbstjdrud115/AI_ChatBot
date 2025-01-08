package com.ll.chat_ai.common.initData;

import com.ll.chat_ai.article.service.ArticleService;
import com.ll.chat_ai.chatting.entity.chattingRoomEntity;
import com.ll.chat_ai.chatting.service.chattingService;
import com.ll.chat_ai.member.service.MemberService;
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
    public ApplicationRunner initNotProd(chattingService chattingService, MemberService memberService,
                                         ArticleService articleService) {
        return args -> {

            String[] activeProfiles = environment.getActiveProfiles();

            /*방정보 생성*/
            chattingRoomEntity chattingRoomEntity1 =  chattingService.makeInitData("공부");
            chattingRoomEntity chattingRoomEntity2 =  chattingService.makeInitData("식사");
            chattingRoomEntity chattingRoomEntity3 =  chattingService.makeInitData("휴식");
            IntStream.rangeClosed(1, 5).forEach(num -> {
                String numAsString = String.valueOf(num);
                chattingService.makeInitMessage(chattingRoomEntity1, "사용자1", numAsString);
            });

            /*사용자 생성*/
          /*  Member member1 = memberService.join("user1", "1234").getData();
            Member member2 = memberService.join("user2", "1234").getData();
            Member member3 = memberService.join("user3", "1234").getData();
*/
            /*게시물?생성*/
            /*Article article1 = articleService.write(member1.getId(), "제목1", "내용1").getData();
            Article article2 = articleService.write(member1.getId(), "제목2", "내용2").getData();
            Article article3 = articleService.write(member2.getId(), "제목3", "내용3").getData();
            Article article4 = articleService.write(member2.getId(), "제목4", "내용4").getData();
*/
            /*댓글생성*/
            /*article1.addComment(member1, "댓글1");
            article1.addComment(member1, "댓글2");
            article2.addComment(member1, "댓글3");
            article2.addComment(member1, "댓글4");
            article2.addComment(member1, "댓글5");
            article3.addComment(member1, "댓글5");
            article3.addComment(member1, "댓글6");
            article3.addComment(member1, "댓글7");
            article3.addComment(member1, "댓글8");
            article3.addComment(member1, "댓글9");
            article3.addComment(member1, "댓글10");
            article3.addComment(member1, "댓글11");
            article3.addComment(member1, "댓글12");
*/
        };
    }


}
