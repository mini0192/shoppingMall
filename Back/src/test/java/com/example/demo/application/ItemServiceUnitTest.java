package com.example.demo.application;

import com.example.demo.Item;
import com.example.demo.infrastructure.ItemRepository;
import com.example.demo.presentation.ItemDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceUnitTest {

    @Mock ItemRepository itemRepository;
    @Mock ValidationService validationService;

    @InjectMocks ItemService itemService;

    @Test
    @DisplayName("findAll() 모든 리스트 받아오기")
    void test_findAll() {
        List<Item> items = new ArrayList<>();
        items.add(Item.builder()
                .id(1L)
                .name("pen")
                .price(1000)
                .build());
        items.add(Item.builder()
                .id(2L)
                .name("pen2")
                .price(2000)
                .build());

        when(itemRepository.findAll()).thenReturn(items);

        List<ItemDto> checkItem = items.stream()
                        .map(ItemDto::toDto)
                        .toList();

        assertTrue(checkEqualList(itemService.findAll(), checkItem));
    }

    @Test
    @DisplayName("findByID() id로 값 찾기 테스트")
    void test_findById() {
        Long itemId = 1L;
        Item item = Item.builder()
                .id(itemId)
                .name("pen")
                .price(1000)
                .build();

        ItemDto checkItem = ItemDto.toDto(item);

        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        assertTrue(checkEqual(itemService.findById(itemId), checkItem));
    }

    boolean checkEqual(ItemDto check, ItemDto target) {
        if(!Objects.equals(check.getId(), target.getId())) return false;
        if(!Objects.equals(check.getName(), target.getName())) return false;
        if(!Objects.equals(check.getPrice(), target.getPrice())) return false;
        return true;
    }

    boolean checkEqualList(List<ItemDto> check, List<ItemDto> target) {
        if(check.size() != target.size()) return false;
        for(int i = 0; i < check.size(); i++) {
            if(!checkEqual(check.get(i), target.get(i))) return false;
        }
        return true;
    }
}