package com.example.demo.application;

import com.example.demo.Item;
import com.example.demo.infrastructure.ItemRepository;
import com.example.demo.presentation.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ValidationService validationService;

    public void add(ItemDto itemDto) {
        Item item = ItemDto.toEntity(itemDto);
        validationService.checkValid(item);
        itemRepository.save(item);
    }

    public List<ItemDto> findAll() {
        List<Item> item = itemRepository.findAll();
        return item.stream()
                .map(ItemDto::toDto)
                .toList();
    }

    public ItemDto findById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isEmpty()) return null;
        Item item = optionalItem.get();
        return ItemDto.toDto(item);
    }

    public void put(Long id, ItemDto itemDto) {
        itemDto.setId(id);
        Item item = ItemDto.toEntity(itemDto);
        validationService.checkValid(item);
        itemRepository.save(item);
    }

    public void delete(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isEmpty()) return;
        Item item = optionalItem.get();
        itemRepository.delete(item);
    }
}
