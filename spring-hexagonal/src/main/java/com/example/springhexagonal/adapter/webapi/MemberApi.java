package com.example.springhexagonal.adapter.webapi;

import com.example.springhexagonal.adapter.webapi.dto.MemberRegisterResponse;
import com.example.springhexagonal.application.member.provided.MemberRegister;
import com.example.springhexagonal.domain.member.Member;
import com.example.springhexagonal.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {

    private final MemberRegister memberRegister;

    // register api -> /members POST
    @PostMapping("/api/members")
    public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberRegister.register(request);

        return MemberRegisterResponse.of(member);
    }
}
