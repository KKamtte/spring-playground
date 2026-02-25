package com.example.springhexagonal.application.required;

import com.example.springhexagonal.domain.Email;
import com.example.springhexagonal.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 회원 정보를 저장하거나 조회한다
 */
public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long id);
}
