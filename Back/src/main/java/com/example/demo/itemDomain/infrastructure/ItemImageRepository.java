package com.example.demo.itemDomain.infrastructure;

import com.example.demo.itemDomain.domain.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    List<ItemImage> findByItem_id(Long id);
}
