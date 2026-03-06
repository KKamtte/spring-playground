package com.example.springhexagonal.application.member.provided;

import com.example.springhexagonal.domain.member.Member;
import com.example.springhexagonal.domain.member.MemberInfoUpdateRequest;
import com.example.springhexagonal.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원의 등록과 관련된 기능을 제공
 */
public interface MemberRegister {

    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);

    Member deactivate(Long memberId);

    Member updateInfo(Long memberId, @Valid MemberInfoUpdateRequest memberInfoUpdateRequest);
}
