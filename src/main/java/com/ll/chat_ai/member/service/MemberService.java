package com.ll.chat_ai.member.service;

import com.ll.chat_ai.common.rsData.resultData;
import com.ll.chat_ai.member.entity.Member;
import com.ll.chat_ai.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public resultData<Member> join(String username, String number) {

        Member member = Member.builder().username(username).number(number).build();
        memberRepository.save(member);
        return resultData.of("200", "%s 님 가입을 환영합니다.".formatted(username),  member);
    }
}
