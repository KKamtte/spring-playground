package com.example.springhexagonal.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileTest {

    @Test
    void profile() {
        new Profile("test11");
        new Profile("test22");
        new Profile("");
    }

    @Test
    void profileFail() {
        // 15 자리 넘는 경우
        assertThatThrownBy(() -> new Profile("a".repeat(16)))
                .isInstanceOf(IllegalArgumentException.class);

        // 대문자
        assertThatThrownBy(() -> new Profile("A"))
                .isInstanceOf(IllegalArgumentException.class);

        // 한글
        assertThatThrownBy(() -> new Profile("한글"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        var profile = new Profile("test11");

        assertThat(profile.url()).isEqualTo("@test11");
    }
}