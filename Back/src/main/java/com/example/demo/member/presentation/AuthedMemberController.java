package com.example.demo.member.presentation;

import com.example.demo.member.application.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/authed")
@PreAuthorize("isAuthenticated()")
public class AuthedMemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberDto> getUserData(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 유저 데이터 호출", ip);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDto retnMemberDto = memberService.getUserData(authentication);
        return new ResponseEntity<>(retnMemberDto, HttpStatus.OK);
    }
}
