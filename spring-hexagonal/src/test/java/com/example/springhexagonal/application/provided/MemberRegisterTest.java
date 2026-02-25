package com.example.springhexagonal.application.provided;

import com.example.springhexagonal.HexagonalTestConfiguration;
import com.example.springhexagonal.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(HexagonalTestConfiguration.class)
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        invalidRequest(new MemberRegisterRequest("swye199@gmail.com", "k", "longSecret"));
        invalidRequest(new MemberRegisterRequest("swye199@gmail.com", "k".repeat(25), "longSecret"));
        invalidRequest(new MemberRegisterRequest("swye199gmail", "kkkkkk", "longSecret"));
    }

    @Test
    void activate() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void demonstrateClear() {
        // 1. 회원 등록 후 flush → INSERT SQL이 DB로 전송됨
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();

        // 2. DB를 직접 수정 (1차 캐시를 우회)
        entityManager.createNativeQuery("UPDATE member SET status = 'ACTIVE' WHERE id = :id")
                .setParameter("id", member.getId())
                .executeUpdate();

        // 3. clear() 없이 조회 → 1차 캐시에서 반환 (DB 변경 내용이 반영 안 됨)
        Member fromCache = entityManager.find(Member.class, member.getId());
        assertThat(fromCache.getStatus()).isEqualTo(MemberStatus.PENDING); // 여전히 PENDING

        // 4. clear() 후 조회 → DB에서 SELECT 실행 (DB 변경 내용 반영됨)
        entityManager.clear();
        Member fromDb = entityManager.find(Member.class, member.getId());
        assertThat(fromDb.getStatus()).isEqualTo(MemberStatus.ACTIVE); // DB의 ACTIVE 반환
    }

    private void invalidRequest(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
