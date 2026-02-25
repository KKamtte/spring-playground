package com.example.springhexagonal.application.provided;

import com.example.springhexagonal.domain.Member;

/**
 * 회원을 조회한다
 */
public interface MemberFinder {
    Member find(Long memberId);
}
