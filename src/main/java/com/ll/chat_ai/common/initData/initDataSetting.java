package com.ll.chat_ai.common.initData;

import com.ll.chat_ai.article.entity.Article;
import com.ll.chat_ai.article.repository.ArticleRepository;
import com.ll.chat_ai.article.service.ArticleService;
import com.ll.chat_ai.chatting.entity.chattingRoomEntity;
import com.ll.chat_ai.chatting.service.chattingService;
import com.ll.chat_ai.member.entity.Member;
import com.ll.chat_ai.member.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
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
    public ApplicationRunner initNotProd(chattingService chattingService
            , MemberService memberService
            , ArticleService articleService
            , ArticleRepository articleRepository
    ) {
        return new ApplicationRunner() {
            @Transactional
            @Override //@Transaction이 없으면 더티체크 기능을 못쓰기에, 이와같이 하였음.
            public void run(ApplicationArguments args) throws Exception {
            String[] activeProfiles = environment.getActiveProfiles();

            /*방정보 생성*/
            chattingRoomEntity chattingRoomEntity1 = chattingService.makeInitData("공부");
            chattingRoomEntity chattingRoomEntity2 = chattingService.makeInitData("식사");
            chattingRoomEntity chattingRoomEntity3 = chattingService.makeInitData("휴식");
            IntStream.rangeClosed(1,5).

            forEach(num ->

            {
                String numAsString = String.valueOf(num);
                chattingService.makeInitMessage(chattingRoomEntity1, "사용자1", numAsString);
            });

            /*사용자 생성*/
            Member member1 = memberService.join("user1", "1234").getData();
            Member member2 = memberService.join("user2", "1234").getData();
            Member member3 = memberService.join("user3", "1234").getData();

            /*게시물?생성*/
            Article article1 = articleService.write(member1.getId(), "제목1", "내용1").getData();
            Article article2 = articleService.write(member1.getId(), "제목2", "내용2").getData();
            Article article3 = articleService.write(member2.getId(), "제목3", "내용3").getData();
            Article article4 = articleService.write(member2.getId(), "제목4", "내용4").getData();

            /*댓글생성.
             * 근데 addComment만 가지고 실제 entity에 데이터가 삽입되지
             * 않는 이유는, @Transaction이 안걸려있기 때문이다.
             *
             *
             * */
            article1.addComment(member1,"댓글1");
            article1.addComment(member1,"댓글2");
            article2.addComment(member1,"댓글3");
            article2.addComment(member1,"댓글5");
            article3.addComment(member1,"댓글5");
            article3.addComment(member1,"댓글12");

            articleRepository.save(article1);
            articleRepository.save(article2);
            articleRepository.save(article3);


            article1.addTag("자바");
            article1.addTag("백엔드");
            article2.addTags("프레임워크", "스프링부트");
            article4.addTags("자바", "스프링부트");
            }
        };
    }
};
