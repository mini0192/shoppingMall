package com.example.demo.presentation;

import com.example.demo.application.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public String add(@RequestBody @Valid ItemDto itemDto) {
        itemService.add(itemDto);
        return "Successful";
    }

    @PutMapping("/{id}")
    public String put(@RequestBody @Valid ItemDto itemDto,
                      @PathVariable("id") Long id) {
        itemService.put(id, itemDto);
        return "Successful";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        itemService.delete(id);
        return "Successful";
    }

    @GetMapping
    public List<ItemDto> findAll() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ItemDto findById(@PathVariable("id") Long id) {
        return itemService.findById(id);
    }
}
