package com.example.demo.itemDomain.application;

import com.example.demo.itemDomain.domain.Item;
import com.example.demo.itemDomain.domain.ItemImage;
import com.example.demo.config.exceotion.FileException;
import com.example.demo.config.exceotion.NotFountDataException;
import com.example.demo.itemDomain.infrastructure.ItemImageRepository;
import com.example.demo.itemDomain.infrastructure.ItemRepository;
import com.example.demo.itemDomain.presentation.ItemDto;
import com.example.demo.itemDomain.presentation.ShowItemDto;
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
    private final FileValidationService fileValidationService;

    private List<String> saveFile(Item takenParente, List<MultipartFile> takenImages) {
        
        String serverPath = "C:/Users/parkgw/Desktop/files/";
        List<String> serverFileNameList = new ArrayList<>();

        takenImages.forEach(fileValidationService::checkImage);

        for(MultipartFile image : takenImages) {
            String originFileName = image.getOriginalFilename();
            String serverFileName = UUID.randomUUID() + originFileName;
            serverFileNameList.add(serverFileName);
            
            String serverSavePath = serverPath + serverFileName;
            try {
                image.transferTo(new File(serverSavePath));
            } catch (IOException e) {
                throw new FileException("파일 생성 실패");
            }
            
            ItemImage itemImage = ItemImage.builder()
                    .originFilename(originFileName)
                    .serverFilename(serverFileName)
                    .item(takenParente)
                    .build();
            
            itemImageRepository.save(itemImage);
        }
        return serverFileNameList;

    }

    @Transactional
    public ShowItemDto add(ItemDto takenItemDto, List<MultipartFile> takenPreviewImageList) {

        Item takenItem = ItemDto.toEntity(takenItemDto);
        validationService.checkValid(takenItem);
        itemRepository.save(takenItem);

        Long savedId = takenItem.getId();
        Optional<Item> rtnRepositoryItem = itemRepository.findById(savedId);
        if(rtnRepositoryItem.isEmpty()) throw new FileException("파일 생성 실패");
        Item savedItem = rtnRepositoryItem.get();
        
        List<String> previewImageList = saveFile(savedItem, takenPreviewImageList);
        return ShowItemDto.toShowItemDto(savedItem, previewImageList);

    }

    public List<ShowItemDto> findAll() {

        List<Item> retnRepositoryItemList = itemRepository.findAll();
        List<ShowItemDto> giveShowItemDtoList = new ArrayList<>();

        for(Item itemLoop : retnRepositoryItemList) {
            List<ItemImage> itemImageList = itemLoop.getImageList();
            List<String> previewImageFileNameList = itemImageList.stream()
                    .map(ItemImage::getServerFilename)
                    .toList();
            giveShowItemDtoList.add(ShowItemDto.toShowItemDto(itemLoop, previewImageFileNameList));
        }

        return giveShowItemDtoList;

    }

    public ShowItemDto findById(Long takenId) {

        Optional<Item> retnRepositoryItem = itemRepository.findById(takenId);
        if(retnRepositoryItem.isEmpty()) throw new NotFountDataException("해당 Id를 찾을 수 없습니다");
        Item savedItem = retnRepositoryItem.get();

        List<ItemImage> itemImageList = savedItem.getImageList();
        List<String> previewImageFileNameList = itemImageList.stream()
                .map(ItemImage::getServerFilename)
                .toList();

        return ShowItemDto.toShowItemDto(savedItem, previewImageFileNameList);

    }

    @Transactional
    public ShowItemDto put(Long takenId, ItemDto takenItemDto, List<MultipartFile> takenPreviewImage) {
        Optional<Item> retnRepositoryItem = itemRepository.findById(takenId);
        if(retnRepositoryItem.isEmpty()) throw new NotFountDataException("해당 Id를 찾을 수 없습니다");
        Item savedItem = retnRepositoryItem.get();

        savedItem.setName(takenItemDto.getName());
        savedItem.setPrice(takenItemDto.getPrice());
        validationService.checkValid(savedItem);

        if(takenPreviewImage == null) {
            List<String> imageList = savedItem.getImageList().stream()
                    .map(ItemImage::getServerFilename)
                    .toList();
            return ShowItemDto.toShowItemDto(savedItem, imageList);
        }

        List<ItemImage> retnRepositryItamImageList = itemImageRepository.findByItem_id(takenId);
        retnRepositryItamImageList.forEach(itemImageRepository::delete);
        itemRepository.save(savedItem);
        List<String> previewImageFileNameList = saveFile(savedItem, takenPreviewImage);

        return ShowItemDto.toShowItemDto(savedItem, previewImageFileNameList);

    }

    @Transactional
    public void delete(Long takenId) {
        Optional<Item> retnRepositoryItem = itemRepository.findById(takenId);
        if(retnRepositoryItem.isEmpty()) throw new NotFountDataException("해당 Id를 찾을 수 없습니다");
        itemRepository.delete(retnRepositoryItem.get());
    }
}