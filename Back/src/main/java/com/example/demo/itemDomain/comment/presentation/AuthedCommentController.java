package com.example.demo.itemDomain.comment.presentation;

import com.example.demo.itemDomain.comment.application.CommentService;
import com.example.demo.member.domain.MemberDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/item/comment/authed")
@PreAuthorize("isAuthenticated()")
public class AuthedCommentController {

    private final CommentService commentService;

    @PostMapping("/{itemId}")
    public ResponseEntity<CommentDto> add(@PathVariable("itemId") Long id,
                                          @RequestBody @Valid CommentDto commentDto,
                                          HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 댓글 생성 호출", ip);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails member = (MemberDetails) authentication.getPrincipal();

        commentDto.setName(member.getName());
        commentDto.setUsername(member.getUsername());
        CommentDto retnCommentDto = commentService.add(id, commentDto);

        return new ResponseEntity<>(retnCommentDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> put(@PathVariable("id") Long id,
                                          @RequestBody @Valid CommentDto commentDto,
                                          HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 댓글 수정 호출", ip);
        commentDto.setId(id);
        CommentDto retnCommentDto = commentService.put(commentDto);
        return new ResponseEntity<>(retnCommentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id,
                                         HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 댓글 삭제 호출", ip);
        commentService.delete(id);
        return new ResponseEntity<>("삭제가 완료되었습니다", HttpStatus.OK);
    }
}
