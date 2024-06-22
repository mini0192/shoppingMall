package com.example.demo.itemDomain.comment.presentation;

import com.example.demo.itemDomain.comment.application.CommentService;
import com.example.demo.member.domain.MemberDetails;
import com.example.demo.member.presentation.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/item/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{itemId}")
    public ResponseEntity<Page<CommentDto>> findAll(@PageableDefault(page=1) Pageable pageable,
                                                    @PathVariable("itemId") Long id,
                                                    HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 댓글 목록 호출", ip);
        Page<CommentDto> retnCommentDtoPage = commentService.findAll(id, pageable);
        return new ResponseEntity<>(retnCommentDtoPage, HttpStatus.OK);
    }
}
