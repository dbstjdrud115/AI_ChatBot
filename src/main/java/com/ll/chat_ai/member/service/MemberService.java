package com.ll.chat_ai.member.service;

import com.ll.chat_ai.common.rsData.resultData;
import com.ll.chat_ai.member.entity.Member;
import com.ll.chat_ai.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public resultData<Member> join(String username, String password) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .build();

        memberRepository.save(member);

        return resultData.of("200", "%s 님 가입을 환영합니다.".formatted(username),  member);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
}