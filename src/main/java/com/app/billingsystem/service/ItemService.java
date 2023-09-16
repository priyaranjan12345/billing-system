package com.app.billingsystem.service;

import com.app.billingsystem.models.dtos.ItemDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;

public interface ItemService {
     ItemDto addItem(ItemDto itemDto) throws IOException;
     ItemDto updateItem(int itemId);
     void deleteItem(int itemId);

}
