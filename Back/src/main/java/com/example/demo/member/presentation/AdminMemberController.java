package com.example.demo.member.presentation;

import com.example.demo.member.application.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/admin")
@Secured("ROLE_ADMIN")
public class AdminMemberController {

    private final MemberService memberService;

    @PutMapping("/lock/{id}")
    public ResponseEntity<String> lock(@PathVariable("id") Long id,
                                       @RequestParam("lock") @NotBlank String lock,
                                       HttpServletRequest request) throws InvalidContentTypeException {
        if(!lock.equals("true") && !lock.equals("false")) throw new InvalidContentTypeException();
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 유저 잠금 설정 호출", ip);
        memberService.lock(id, lock);
        return new ResponseEntity<>("잠금 설정이 완료되었습니다.", HttpStatus.OK);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<String> admin(@PathVariable("id") Long id,
                                        @RequestParam("admin") @NotBlank String admin,
                                        HttpServletRequest request) throws InvalidContentTypeException {
        if(!admin.equals("true") && !admin.equals("false")) throw new InvalidContentTypeException();
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 관리자 권한 설정 호출", ip);
        memberService.updateRole(id, admin);
        return new ResponseEntity<>("관리자 권한 설정이 완료되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Page<MemberDto>> findAll(@PageableDefault(page=1) Pageable pageable,
                                                   @RequestParam(value = "count", defaultValue = "4") int count,
                                                   @RequestParam(value = "locked", defaultValue = "") String locked,
                                                   HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 유저 목록 호출", ip);
        Page<MemberDto> retnMemberDtoPage = memberService.findAll(pageable, count, locked);
        return new ResponseEntity<>(retnMemberDtoPage, HttpStatus.OK);
    }
}
