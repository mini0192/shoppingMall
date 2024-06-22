package com.example.demo.member.presentation;

import com.example.demo.member.application.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/add")
    public ResponseEntity<MemberDto> add(@RequestBody @Valid MemberDto memberDto,
                                         HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 유저 생성 호출", ip);
        MemberDto retnMemberDto = memberService.add(memberDto);
        return new ResponseEntity<>(retnMemberDto, HttpStatus.CREATED);
    }

}
