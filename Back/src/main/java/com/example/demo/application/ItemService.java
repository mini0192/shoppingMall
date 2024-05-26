package com.example.demo.application;

import com.example.demo.domain.Item;
import com.example.demo.domain.ItemImage;
import com.example.demo.config.exceotion.FileException;
import com.example.demo.config.exceotion.NotFountDataException;
import com.example.demo.infrastructure.ItemImageRepository;
import com.example.demo.infrastructure.ItemRepository;
import com.example.demo.presentation.ItemDto;
import com.example.demo.presentation.ShowItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final ValidationService validationService;

    private void saveFile(Item parente, List<MultipartFile> images) {
        String filePath = "C:/Users/parkgw/Desktop/files/";
        for(MultipartFile image : images) {
            String originFileName = image.getOriginalFilename();
            String serverFileName = UUID.randomUUID().toString() + originFileName;
            String savePath = filePath + serverFileName;
            try {
                image.transferTo(new File(savePath));
            } catch (IOException e) {
                throw new FileException("파일 생성 실패");
            }
            ItemImage itemImage = ItemImage.builder()
                    .originFilename(originFileName)
                    .serverFilename(serverFileName)
                    .item(parente)
                    .build();
            itemImageRepository.save(itemImage);
        }
    }

    @Transactional
    public void add(ItemDto itemDto, List<MultipartFile> previewImage) {
        Item item = ItemDto.toEntity(itemDto);
        validationService.checkValid(item);
        itemRepository.save(item);

        Long saveId = item.getId();
        Optional<Item> optionalItem = itemRepository.findById(saveId);
        if(optionalItem.isEmpty()) throw new FileException("파일 생성 실패[DB Error]");

        Item saveItem = optionalItem.get();
        saveFile(saveItem, previewImage);
    }

    public List<ShowItemDto> findAll() {
        List<Item> item = itemRepository.findAll();
        List<ShowItemDto> showItemDtos = new ArrayList<>();
        for(Item item1 : item) {
            List<ItemImage> itemImages = item1.getImageList();
            List<String> previewServerPath = itemImages.stream()
                    .map(ItemImage::getServerFilename)
                    .toList();
            showItemDtos.add(ShowItemDto.toShowItemDto(item1, previewServerPath));
        }
        return showItemDtos;
    }

    public ShowItemDto findById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isEmpty()) throw new NotFountDataException("해당 Id를 찾을 수 없습니다");
        Item item = optionalItem.get();
        List<ItemImage> itemImages = item.getImageList();
        List<String> serverFilename = itemImages.stream()
                .map(ItemImage::getServerFilename)
                .toList();

        return ShowItemDto.toShowItemDto(item, serverFilename);
    }

    public void put(Long id, ItemDto itemDto, List<MultipartFile> previewImage) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isEmpty()) throw new NotFountDataException("해당 Id를 찾을 수 없습니다");

        Item item = optionalItem.get();
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());

        validationService.checkValid(item);
        itemRepository.save(item);
        saveFile(item, previewImage);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if(optionalItem.isEmpty()) throw new NotFountDataException("해당 Id를 찾을 수 없습니다");
        itemRepository.delete(optionalItem.get());
    }
}