package com.example.demo.itemDomain.item.infrastructure;

import com.example.demo.itemDomain.item.domain.Item;
import com.example.demo.itemDomain.item.domain.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    void deleteByItem(Item item);
}
