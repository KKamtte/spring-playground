package com.example.springhexagonal.application;

import com.example.springhexagonal.application.provided.MemberRegister;
import com.example.springhexagonal.application.required.EmailSender;
import com.example.springhexagonal.application.required.MemberRepository;
import com.example.springhexagonal.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        /* 파라미터, 시스템 check */
        checkDuplicateEmail(registerRequest);

        /* domain model 을 통해 주요 로직 처리 */
        Member member = Member.register(registerRequest, passwordEncoder);

        /* repository 를 통해 저장 */
        memberRepository.save(member);

        /* post process */
        sendRegisterEmail(member);

        return member;
    }

    private void sendRegisterEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.email());
        }
    }
}
