package com.example.demo.itemDomain.comment.presentation;

import com.example.demo.itemDomain.comment.domain.Comment;
import com.example.demo.itemDomain.item.domain.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String review;

    public static Comment toEntity(CommentDto commentDto, Item item) {
        return Comment.builder()
                .id(commentDto.getId())
                .name(commentDto.getName())
                .review(commentDto.getReview())
                .item(item)
                .build();
    }

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .review(comment.getReview())
                .build();
    }
}
