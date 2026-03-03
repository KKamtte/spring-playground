package com.example.springhexagonal.adapter.integration;

import com.example.springhexagonal.domain.Email;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(new Email("swye199@gmail.com"), "subject", "body");
//        System.out.println("") 에 대한 출력 검증 테스트
//        assertThat(out.capturedLines()[0]).isEqualTo("");
    }

}