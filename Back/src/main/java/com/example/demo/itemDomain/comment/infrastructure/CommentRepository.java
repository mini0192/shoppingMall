package com.example.demo.itemDomain.comment.infrastructure;

import com.example.demo.itemDomain.comment.domain.Comment;
import com.example.demo.itemDomain.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByItemOrderByIdDesc(Item item);
}
