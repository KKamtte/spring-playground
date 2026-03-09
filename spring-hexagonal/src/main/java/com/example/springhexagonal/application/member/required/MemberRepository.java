package com.example.springhexagonal.application.member.required;

import com.example.springhexagonal.domain.member.Member;
import com.example.springhexagonal.domain.member.Profile;
import com.example.springhexagonal.domain.shared.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * 회원 정보를 저장하거나 조회한다
 */
public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long id);

    @Query("SELECT m FROM Member m WHERE m.detail.profile = :profile")
    Optional<Member> findByProfile(Profile profile);
}
