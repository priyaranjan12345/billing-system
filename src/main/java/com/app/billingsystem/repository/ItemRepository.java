package com.app.billingsystem.repository;

import com.app.billingsystem.models.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Integer> {
}
