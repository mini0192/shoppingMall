package com.example.demo.itemDomain.comment.domain;

import com.example.demo.config.BaseTime;
import com.example.demo.itemDomain.item.domain.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    @Size(min = 1, max = 10)
    private String username;

    @Column
    @Size(min = 1, max = 10)
    private String name;

    @Column
    @Size(min = 1, max = 100)
    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    public void updateComment(String review) {
        this.review = review;
    }
}
