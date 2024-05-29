package com.example.demo.itemDomain.item.presentation;

import com.example.demo.itemDomain.item.domain.Item;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShowItemDto {
    private Long id;
    private String name;
    private Integer price;
    private List<String> previewImage;

    public static ShowItemDto toShowItemDto(Item item, List<String> previewImage) {
        return ShowItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .previewImage(previewImage)
                .build();
    }
}
