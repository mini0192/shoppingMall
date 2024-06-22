package com.example.demo.itemDomain.item.presentation;

import com.example.demo.itemDomain.item.domain.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDto {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private String content;
    private String type;
    private String deleted;
    private List<String> ImageList;

    public static Item toEntity(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .price(itemDto.getPrice())
                .content(itemDto.getContent())
                .type(itemDto.getType())
                .deleted(itemDto.getDeleted())
                .build();
    }

    public static ItemDto toDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .content(item.getContent())
                .type(item.getType())
                .deleted(item.getDeleted())
                .build();
    }

    public static ItemDto toItemDto(Item item, List<String> image) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .content(item.getContent())
                .type(item.getType())
                .deleted(item.getDeleted())
                .ImageList(image)
                .build();
    }
}
