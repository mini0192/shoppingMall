package com.example.demo.presentation;

import com.example.demo.application.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ShowItemDto add(@RequestPart(value = "item") @Valid ItemDto itemDto,
                      @RequestPart(value = "previewImage", required = false) List<MultipartFile> previewImage,
                      HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called ItemController.add()", ip);
        return itemService.add(itemDto, previewImage);
    }

    @PutMapping("/{id}")
    public ShowItemDto put(@RequestPart(value = "item") @Valid ItemDto itemDto,
                      @RequestPart(value = "previewImage", required = false) List<MultipartFile> previewImage,
                      @PathVariable("id") Long id,
                      HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called ItemController.put()", ip);
        return itemService.put(id, itemDto, previewImage);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called ItemController.delete()", ip);
        itemService.delete(id);
        return "Successful";
    }

    @GetMapping
    public List<ShowItemDto> findAll(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called ItemController.findAll()", ip);
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ShowItemDto findById(@PathVariable("id") Long id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called ItemController.findById()", ip);
        return itemService.findById(id);
    }
}
