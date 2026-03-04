package com.example.springhexagonal.domain;

import com.example.springhexagonal.domain.shared.Email;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @Test
    void equality() {
        var email1 = new Email("test@test.com");
        var email2 = new Email("test@test.com");

        assertThat(email1).isEqualTo(email2);
    }

}