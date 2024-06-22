package com.example.demo.itemDomain.comment.application;

import com.example.demo.config.exceotion.NotFountDataException;
import com.example.demo.itemDomain.comment.domain.Comment;
import com.example.demo.itemDomain.comment.infrastructure.CommentRepository;
import com.example.demo.itemDomain.comment.presentation.CommentDto;
import com.example.demo.config.ValidationService;
import com.example.demo.itemDomain.item.domain.Item;
import com.example.demo.itemDomain.item.infrastructure.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;

    private final ValidationService validationService;

    @Transactional
    public CommentDto add(Long takenId, CommentDto takenCommentDto) {
        Item savedItem = itemRepository.findById(takenId)
                .orElseThrow(() -> new NotFountDataException("해당 상품을 찾을 수 없습니다."));

        Comment comment = CommentDto.toEntity(takenCommentDto, savedItem);
        validationService.checkValid(comment);
        return CommentDto.toDto(commentRepository.save(comment));

    }

    public Page<CommentDto> findAll(Long takenId, Pageable takenPageable) {

        Item savedItem = itemRepository.findById(takenId)
                .orElseThrow(() -> new NotFountDataException("해당 상품을 찾을 수 없습니다."));

        int page = takenPageable.getPageNumber() - 1;
        int pageLimit = 5;
        Pageable pageable = PageRequest.of(page, pageLimit);

        List<Comment> retnRepositoryCommentList = commentRepository.findByItemOrderByIdDesc(savedItem);
        List<CommentDto> givenCommentDtoList = retnRepositoryCommentList.stream().map(CommentDto::toDto).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()),givenCommentDtoList.size());
        return new PageImpl<>(givenCommentDtoList.subList(start, end), pageable, givenCommentDtoList.size());

    }

    @Transactional
    public CommentDto put(CommentDto takenCommentDto) {
        Long takenId = takenCommentDto.getId();
        Comment savedComment = commentRepository.findById(takenId)
                .orElseThrow(() -> new NotFountDataException("해당 댓글을 찾을 수 없습니다."));

        savedComment.updateComment(takenCommentDto.getReview());
        validationService.checkValid(savedComment);
        return CommentDto.toDto(commentRepository.save(savedComment));

    }

    @Transactional
    public void delete(Long id) {
        Comment savedComment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFountDataException("해당 댓글을 찾을 수 없습니다."));
        commentRepository.delete(savedComment);
    }
}
