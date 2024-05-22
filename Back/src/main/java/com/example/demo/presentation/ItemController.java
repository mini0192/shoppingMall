package com.example.demo.presentation;

import com.example.demo.application.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public String add(@RequestBody @Valid ItemDto itemDto, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called add()", ip);
        itemService.add(itemDto);
        return "Successful";
    }

    @PutMapping("/{id}")
    public String put(@RequestBody @Valid ItemDto itemDto,
                      @PathVariable("id") Long id,
                      HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called put()", ip);
        itemService.put(id, itemDto);
        return "Successful";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called delete()", ip);
        itemService.delete(id);
        return "Successful";
    }

    @GetMapping
    public List<ItemDto> findAll(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called findAll()", ip);
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ItemDto findById(@PathVariable("id") Long id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called findById()", ip);
        return itemService.findById(id);
    }
}
