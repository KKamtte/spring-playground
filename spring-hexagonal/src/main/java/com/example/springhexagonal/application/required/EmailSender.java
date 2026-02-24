package com.example.springhexagonal.application.required;

import com.example.springhexagonal.domain.Email;

/**
 * 이메일을 발송한다.
 */
public interface EmailSender {
    void send(Email email, String subject, String body);
}
