package com.example.demo.itemDomain.item.application;

import com.example.demo.config.ValidationService;
import com.example.demo.itemDomain.item.domain.Item;
import com.example.demo.itemDomain.item.domain.ItemImage;
import com.example.demo.config.exceotion.NotFountDataException;
import com.example.demo.itemDomain.item.infrastructure.FileRepository;
import com.example.demo.itemDomain.item.infrastructure.ItemImageRepository;
import com.example.demo.itemDomain.item.infrastructure.ItemRepository;
import com.example.demo.itemDomain.item.presentation.ItemDto;
import com.example.demo.config.Status;
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
    public ItemDto add(ItemDto takenItemDto, List<MultipartFile> takenPreviewImageList) {
        takenItemDto.setDeleted(Status.FALSE.getStatus());
        Item takenItem = ItemDto.toEntity(takenItemDto);
        validationService.checkValid(takenItem);
        Item savedItem = itemRepository.save(takenItem);
        
        List<String> previewImageList = saveFile(savedItem, takenPreviewImageList);
        return ItemDto.toItemDto(savedItem, previewImageList);

    }

    public Page<ItemDto> findAll(Pageable takenPageable, int takenCount, String takenDeleted) {
        int page = takenPageable.getPageNumber() - 1;
        int pageLimit = takenCount;
        Page<Item> retnRepositoryItemPage;

        if(takenDeleted.isBlank())
            retnRepositoryItemPage =
                    itemRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        else
            retnRepositoryItemPage =
                    itemRepository.findByDeleted(takenDeleted, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        return retnRepositoryItemPage.map(item -> {
            List<ItemImage> itemImageList = item.getImageList();
            List<String> previewImageFileNameList = itemImageList.stream()
                    .map(ItemImage::getServerFilename)
                    .toList();
            return ItemDto.toItemDto(item, previewImageFileNameList);
        });

    }

    public ItemDto findById(Long takenId) {

        Item savedItem = itemRepository.findById(takenId)
                .orElseThrow(() -> new NotFountDataException("해당 상품을 찾을 수 없습니다."));

        if(savedItem.getDeleted().equals("true"))
            throw new NotFountDataException("해당 상품을 찾을 수 없습니다.");

        List<ItemImage> itemImageList = savedItem.getImageList();
        List<String> previewImageFileNameList = itemImageList.stream()
                .map(ItemImage::getServerFilename)
                .toList();

        return ItemDto.toItemDto(savedItem, previewImageFileNameList);
    }

    @Transactional
    public ItemDto put(Long takenId, ItemDto takenItemDto, List<MultipartFile> takenPreviewImage) {
        Item savedItem = itemRepository.findById(takenId)
                .orElseThrow(() -> new NotFountDataException("해당 상품을 찾을 수 없습니다."));

        savedItem.updateItem(takenItemDto.getName(), takenItemDto.getPrice());
        validationService.checkValid(savedItem);

        Item changedItem = itemRepository.save(savedItem);

        if(takenPreviewImage == null) {
            List<String> imageList = savedItem.getImageList().stream()
                    .map(ItemImage::getServerFilename)
                    .toList();
            return ItemDto.toItemDto(savedItem, imageList);
        }

        itemImageRepository.deleteByItem(savedItem);
        List<String> previewImageFileNameList = saveFile(changedItem, takenPreviewImage);

        return ItemDto.toItemDto(savedItem, previewImageFileNameList);

    }

    @Transactional
    public void delete(Long takenId, String takenDeleted) {
        Item savedItem = itemRepository.findById(takenId)
                .orElseThrow(() -> new NotFountDataException("해당 상품을 찾을 수 없습니다."));
        savedItem.updateDeletedStatus(takenDeleted);
        itemRepository.save(savedItem);
    }
}