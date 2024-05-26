package com.example.demo.infrastructure;

import com.example.demo.domain.Item;
import com.example.demo.domain.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    //DELETE FROM table_image WHERE item_id = :id
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ItemImage t WHERE t.item = :item")
    void deleteMappingById(Item item);
}
