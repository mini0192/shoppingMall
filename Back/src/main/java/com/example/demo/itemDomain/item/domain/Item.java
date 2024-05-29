package com.example.demo.itemDomain.item.domain;

import com.example.demo.config.BaseTime;
import com.example.demo.itemDomain.comment.domain.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Item extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(min = 1, max = 100)
    private String name;

    @Column
    @Min(1) @Max(1000000)
    private Integer price;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemImage> imageList;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList;
}
