package com.example.demo.itemDomain.infrastructure;

import com.example.demo.itemDomain.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
