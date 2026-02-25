package com.example.springhexagonal.application.provided;

import com.example.springhexagonal.domain.Member;
import com.example.springhexagonal.domain.MemberRegisterRequest;
import jakarta.validation.Valid;

/**
 * 회원의 등록과 관련된 기능을 제공
 */
public interface MemberRegister {

    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);
}
