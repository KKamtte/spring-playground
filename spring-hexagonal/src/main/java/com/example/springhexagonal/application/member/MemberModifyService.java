package com.example.springhexagonal.application.member;

import com.example.springhexagonal.adapter.security.SecurePasswordEncoder;
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
        /**
         * 프로필 검증 방식
         * 1. 자기 자신을 제외하고 Repository 에서 호출 후 검사
         * 2. 멤버를 가져온 후 프로필이 변경되었는지 확인 후 변경되었다면 Repository 에서 중복 검사
         */
        Member member = memberFinder.find(memberId);

        checkDuplicateProfile(member, memberInfoUpdateRequest.profileAddress());

        member.updateInfo(memberInfoUpdateRequest);

        return memberRepository.save(member);
    }

    private void checkDuplicateProfile(Member member, String profileAddress) {
        if (profileAddress.isEmpty()) {
            return;
        }

        Profile currentProfile = member.getDetail().getProfile();
        if (currentProfile != null && currentProfile.address().equals(profileAddress)) {
            return;
        }

        if (memberRepository.findByProfile(new Profile(profileAddress)).isPresent()) {
            throw new DuplicateProfileException("이미 존재하는 프로필 주소입니다.");
        }
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
