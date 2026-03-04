package com.example.springhexagonal.application.member.provided;

import com.example.springhexagonal.domain.member.Member;

/**
 * 회원을 조회한다
 */
public interface MemberFinder {
    Member find(Long memberId);
}
