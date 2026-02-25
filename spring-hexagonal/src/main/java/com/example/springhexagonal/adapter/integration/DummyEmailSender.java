package com.example.springhexagonal.adapter.integration;

import com.example.springhexagonal.application.required.EmailSender;
import com.example.springhexagonal.domain.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        log.info("Dummy email sending");
    }
}
