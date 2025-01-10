package com.ll.chat_ai.common.initData;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.article.service.ArticleService;
import com.ll.chat_ai.chatting.entity.chattingRoomEntity;
import com.ll.chat_ai.chatting.service.chattingService;
import com.ll.chat_ai.member.entity.Member;
import com.ll.chat_ai.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Profile("!prod")
@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Autowired
    @Lazy
    private NotProd self;
    private final MemberService memberService;
    private final ArticleService articleService;
    private final chattingService ChattingService;

    @Bean
    public ApplicationRunner initNotProdData() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {

        chattingRoomEntity chattingRoomEntity1 =  ChattingService.makeInitData("공부");
        chattingRoomEntity chattingRoomEntity2 =  ChattingService.makeInitData("식사");
        chattingRoomEntity chattingRoomEntity3 =  ChattingService.makeInitData("휴식");
        IntStream.rangeClosed(1, 5).forEach(num -> {
            String numAsString = String.valueOf(num);
            ChattingService.makeInitMessage(chattingRoomEntity1, "사용자1", numAsString);
        });

        Member member1 = memberService.join("user1", "1234").getData();
        Member member2 = memberService.join("user2", "1234").getData();
        Member member3 = memberService.join("user3", "1234").getData();

        Article article1 = articleService.write(member1.getId(), "제목1", "내용1").getData();
        Article article2 = articleService.write(member1.getId(), "제목2", "내용2").getData();

        Article article3 = articleService.write(member2.getId(), "제목3", "내용3").getData();
        Article article4 = articleService.write(member2.getId(), "제목4", "내용4").getData();

        article1.addComment(member1, "댓글1");
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

        article1.addTag("자바");
        article1.addTag("백엔드");
        article2.addTag("프레임워크", "스프링부트");

        article4.addTag("자바", "스프링부트");

        IntStream.rangeClosed(5, 120).forEach(
                i -> {
                    String title = "제목" + i;
                    String body = "내용" + i;
                    articleService.write(member2.getId(), title, body);
                }
        );
    }
}