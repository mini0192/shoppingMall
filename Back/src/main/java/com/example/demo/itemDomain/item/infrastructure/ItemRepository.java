package com.example.demo.itemDomain.item.infrastructure;

import com.example.demo.itemDomain.item.domain.Item;
import com.example.demo.itemDomain.item.domain.ItemImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByDeleted(String deleted, PageRequest page);
}
