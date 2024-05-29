package com.example.demo.itemDomain.item.application;

import com.example.demo.config.ValidationService;
import com.example.demo.itemDomain.item.domain.Item;
import com.example.demo.itemDomain.item.domain.ItemImage;
import com.example.demo.config.exceotion.NotFountDataException;
import com.example.demo.itemDomain.item.infrastructure.FileRepository;
import com.example.demo.itemDomain.item.infrastructure.ItemImageRepository;
import com.example.demo.itemDomain.item.infrastructure.ItemRepository;
import com.example.demo.itemDomain.item.presentation.ItemDto;
import com.example.demo.itemDomain.item.presentation.ShowItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final FileRepository fileRepository;

    private final ValidationService validationService;
    private final ImageValidationService imageValidationService;

    /**
     * 이미지 파일 저장하는 함수
     * */
    private List<String> saveFile(Item takenParente, List<MultipartFile> takenImages) {

        List<String> serverFileNameList = new ArrayList<>();

        takenImages.forEach(image -> {
            imageValidationService.checkImage(image);
            String serverFileName = fileRepository.saveFile(image);

            serverFileNameList.add(serverFileName);
            String originFileName = image.getOriginalFilename();

            ItemImage itemImage = ItemImage.builder()
                    .originFilename(originFileName)
                    .serverFilename(serverFileName)
                    .item(takenParente)
                    .build();
            itemImageRepository.save(itemImage);
        });
        return serverFileNameList;

    }

    @Transactional
    public ShowItemDto add(ItemDto takenItemDto, List<MultipartFile> takenPreviewImageList) {

        Item takenItem = ItemDto.toEntity(takenItemDto);
        validationService.checkValid(takenItem);
        Item savedItem = itemRepository.save(takenItem);
        
        List<String> previewImageList = saveFile(savedItem, takenPreviewImageList);
        return ShowItemDto.toShowItemDto(savedItem, previewImageList);

    }

    public Page<ShowItemDto> findAll(Pageable takenPageable) {
        int page = takenPageable.getPageNumber() - 1;
        int pageLimit = 3;

        Page<Item> retnRepositoryItemPage = itemRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        return retnRepositoryItemPage.map(item -> {
            List<ItemImage> itemImageList = item.getImageList();
            List<String> previewImageFileNameList = itemImageList.stream()
                    .map(ItemImage::getServerFilename)
                    .toList();
            return ShowItemDto.toShowItemDto(item, previewImageFileNameList);
        });

    }

    public ShowItemDto findById(Long takenId) {

        Optional<Item> retnRepositoryItem = itemRepository.findById(takenId);
        if(retnRepositoryItem.isEmpty()) throw new NotFountDataException("해당 상품을 찾을 수 없습니다.");
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
        if(retnRepositoryItem.isEmpty()) throw new NotFountDataException("해당 상품을 찾을 수 없습니다.");
        Item savedItem = retnRepositoryItem.get();

        savedItem.setName(takenItemDto.getName());
        savedItem.setPrice(takenItemDto.getPrice());
        validationService.checkValid(savedItem);

        Item changedItem = itemRepository.save(savedItem);

        if(takenPreviewImage == null) {
            List<String> imageList = savedItem.getImageList().stream()
                    .map(ItemImage::getServerFilename)
                    .toList();
            return ShowItemDto.toShowItemDto(savedItem, imageList);
        }

        itemImageRepository.deleteByItem(savedItem);
        List<String> previewImageFileNameList = saveFile(changedItem, takenPreviewImage);

        return ShowItemDto.toShowItemDto(savedItem, previewImageFileNameList);

    }

    @Transactional
    public void delete(Long takenId) {
        Optional<Item> retnRepositoryItem = itemRepository.findById(takenId);
        if(retnRepositoryItem.isEmpty()) throw new NotFountDataException("해당 상품을 찾을 수 없습니다");
        itemRepository.delete(retnRepositoryItem.get());
    }
}