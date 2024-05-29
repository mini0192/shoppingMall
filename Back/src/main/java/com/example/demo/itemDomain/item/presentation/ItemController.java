package com.example.demo.itemDomain.item.presentation;

import com.example.demo.itemDomain.item.application.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ShowItemDto> add(@RequestPart(value = "item") @Valid ItemDto itemDto,
                                          @RequestPart(value = "previewImage", required = false) @NotNull List<MultipartFile> previewImage,
                                          HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called ItemController.add()", ip);
        ShowItemDto retnShowItemDto = itemService.add(itemDto, previewImage);
        return new ResponseEntity<>(retnShowItemDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowItemDto> put(@RequestPart(value = "item") @Valid ItemDto itemDto,
                      @RequestPart(value = "previewImage", required = false) List<MultipartFile> previewImage,
                      @PathVariable("id") Long id,
                      HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called ItemController.put()", ip);
        ShowItemDto retnShowItemDto = itemService.put(id, itemDto, previewImage);
        return new ResponseEntity<>(retnShowItemDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called ItemController.delete()", ip);
        itemService.delete(id);
        return new ResponseEntity<>("삭제가 완료되었습니다.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ShowItemDto>> findAll(@PageableDefault(page=1) Pageable pageable,
                                                     HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called ItemController.findAll()", ip);
        Page<ShowItemDto> retnShowItemDtoPage = itemService.findAll(pageable);
        return new ResponseEntity<>(retnShowItemDtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowItemDto> findById(@PathVariable("id") Long id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} called ItemController.findById()", ip);
        ShowItemDto retnShowItemDto = itemService.findById(id);
        return new ResponseEntity<>(retnShowItemDto, HttpStatus.OK);
    }
}
