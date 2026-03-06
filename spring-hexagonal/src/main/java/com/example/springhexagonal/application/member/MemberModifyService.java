package com.example.springhexagonal.application.member;

import com.example.springhexagonal.application.member.provided.MemberFinder;
import com.example.springhexagonal.application.member.provided.MemberRegister;
import com.example.springhexagonal.application.member.required.EmailSender;
import com.example.springhexagonal.application.member.required.MemberRepository;
import com.example.springhexagonal.domain.member.*;
import com.example.springhexagonal.domain.shared.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

    private final MemberFinder memberFinder;
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

    @Transactional
    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.activate();

        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public Member deactivate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.deactivate();

        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest memberInfoUpdateRequest) {
        Member member = memberFinder.find(memberId);

        member.updateInfo(memberInfoUpdateRequest);

        return memberRepository.save(member);
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
