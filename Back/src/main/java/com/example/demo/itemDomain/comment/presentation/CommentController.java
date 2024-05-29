package com.example.demo.itemDomain.comment.presentation;

import com.example.demo.itemDomain.comment.application.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{itemId}")
    public ResponseEntity<CommentDto> add(@PathVariable("itemId") Long id,
                                          @RequestPart(value = "comment") @Valid CommentDto commentDto,
                                          HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called CommentController.add()", ip);
        CommentDto retnCommentDto = commentService.add(id, commentDto);
        return new ResponseEntity<>(retnCommentDto, HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<List<CommentDto>> findAll(@PathVariable("itemId") Long id,
                                                    HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called CommentController.findAll()", ip);
        List<CommentDto> retnCommentDtoList = commentService.findAll(id);
        return new ResponseEntity<>(retnCommentDtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> put(@PathVariable("id") Long id,
                                          @RequestPart(value = "comment") @Valid CommentDto commentDto,
                                          HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called CommentController.put()", ip);
        commentDto.setId(id);
        CommentDto retnCommentDto = commentService.put(commentDto);
        return new ResponseEntity<>(retnCommentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id,
                                         HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called CommentController.put()", ip);
        commentService.delete(id);
        return new ResponseEntity<>("삭제가 완료되었습니다", HttpStatus.OK);
    }
}
