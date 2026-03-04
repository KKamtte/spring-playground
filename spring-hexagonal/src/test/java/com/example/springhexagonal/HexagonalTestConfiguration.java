package com.example.springhexagonal;

import com.example.springhexagonal.application.member.required.EmailSender;
import com.example.springhexagonal.domain.MemberFixture;
import com.example.springhexagonal.domain.member.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class HexagonalTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println("Sending email");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
