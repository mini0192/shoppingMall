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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<Page<ItemDto>> findAll(@PageableDefault(page=1) Pageable pageable,
                                                     @RequestParam(value = "count", defaultValue = "4") int count,
                                                     HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 상품 목록 호출", ip);
        Page<ItemDto> retnItemDtoPage = itemService.findAll(pageable, count, "false");
        return new ResponseEntity<>(retnItemDtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> findById(@PathVariable("id") Long id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 상품 상세페이지 호출", ip);
        ItemDto retnItemDto = itemService.findById(id);
        return new ResponseEntity<>(retnItemDto, HttpStatus.OK);
    }
}
