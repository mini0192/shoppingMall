package com.example.demo.itemDomain.comment.application;

import com.example.demo.config.exceotion.NotFountDataException;
import com.example.demo.itemDomain.comment.domain.Comment;
import com.example.demo.itemDomain.comment.infrastructure.CommentRepository;
import com.example.demo.itemDomain.comment.presentation.CommentDto;
import com.example.demo.config.ValidationService;
import com.example.demo.itemDomain.item.domain.Item;
import com.example.demo.itemDomain.item.infrastructure.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;

    private final ValidationService validationService;

    public CommentDto add(Long takenId, CommentDto takenCommentDto) {

        Optional<Item> retnRepositoryItem = itemRepository.findById(takenId);
        if(retnRepositoryItem.isEmpty()) throw new NotFountDataException("해당 상품을 찾을 수 없습니다.");
        Item savedItem = retnRepositoryItem.get();

        Comment comment = CommentDto.toEntity(takenCommentDto, savedItem);
        validationService.checkValid(comment);
        return CommentDto.toDto(commentRepository.save(comment));

    }

    public List<CommentDto> findAll(Long takenId) {

        Optional<Item> retnRepositoryItem = itemRepository.findById(takenId);
        if(retnRepositoryItem.isEmpty()) throw new NotFountDataException("해당 상품을 찾을 수 없습니다.");
        Item savedItem = retnRepositoryItem.get();

        List<Comment> retnRepositoryCommentList = commentRepository.findByItemOrderByIdDesc(savedItem);
        return retnRepositoryCommentList.stream().map(CommentDto::toDto).toList();

    }

    public CommentDto put(CommentDto takenCommentDto) {

        Long takenId = takenCommentDto.getId();
        Optional<Comment> retnRepositoryComment = commentRepository.findById(takenId);
        if(retnRepositoryComment.isEmpty()) throw new NotFountDataException("해당 댓글을 찾을 수 없습니다.");
        Comment savedComment = retnRepositoryComment.get();

        savedComment.setReview(takenCommentDto.getReview());
        validationService.checkValid(savedComment);
        return CommentDto.toDto(commentRepository.save(savedComment));
    }

    public void delete(Long id) {
        Optional<Comment> retnRepositoryComment = commentRepository.findById(id);
        if(retnRepositoryComment.isEmpty()) throw new NotFountDataException("해당 댓글을 찾을 수 없습니다.");
        commentRepository.delete(retnRepositoryComment.get());
    }
}
