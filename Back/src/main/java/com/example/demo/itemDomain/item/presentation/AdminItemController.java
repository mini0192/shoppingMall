package com.example.demo.itemDomain.item.presentation;

import com.example.demo.itemDomain.item.application.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("/item/admin")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class AdminItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<Page<ItemDto>> findAll(@PageableDefault(page=1) Pageable pageable,
                                                     @RequestParam(value = "count", defaultValue = "4") int count,
                                                     @RequestParam(value = "deleted", defaultValue = "") String deleted,
                                                     HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 관리자 상품 목록 호출", ip);
        Page<ItemDto> retnItemDtoPage = itemService.findAll(pageable, count, deleted);
        return new ResponseEntity<>(retnItemDtoPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemDto> add(@RequestPart(value = "item") @Valid ItemDto itemDto,
                                           @RequestPart(value = "image", required = false) @NotNull List<MultipartFile> previewImage,
                                           HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 상품 생성 호출", ip);
        ItemDto retnItemDto = itemService.add(itemDto, previewImage);
        return new ResponseEntity<>(retnItemDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemDto> put(@RequestPart(value = "item") @Valid ItemDto itemDto,
                                           @RequestPart(value = "previewImage", required = false) List<MultipartFile> previewImage,
                                           @PathVariable("id") Long id,
                                           HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 상품 수정 호출", ip);
        ItemDto retnItemDto = itemService.put(id, itemDto, previewImage);
        return new ResponseEntity<>(retnItemDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id,
                                         @RequestParam("deleted") @NotBlank String deleted,
                                         HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        log.info("Connection from: {} -> 상품 삭제 호출", ip);
        itemService.delete(id, deleted);
        return new ResponseEntity<>("삭제가 완료되었습니다.", HttpStatus.OK);
    }
}
