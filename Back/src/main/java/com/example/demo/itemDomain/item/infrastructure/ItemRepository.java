package com.example.demo.itemDomain.item.infrastructure;

import com.example.demo.itemDomain.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
