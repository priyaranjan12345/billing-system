package com.app.billingsystem.service.Impl;

import com.app.billingsystem.models.dtos.ItemDto;
import com.app.billingsystem.models.entities.Item;
import com.app.billingsystem.repository.ItemRepository;
import com.app.billingsystem.service.FileService;
import com.app.billingsystem.service.ItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    public ItemRepository itemRepository;

    public FileService fileService;

    private ModelMapper mapper;


    @Override
    public ItemDto addItem(ItemDto itemDto) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        String filePath=fileService.uploadImage(itemDto.getImage());
        Item item= Item.builder()
                .name(itemDto.getName())
                .price(itemDto.getPrice())
                .image(filePath.toString())
                .description((itemDto.getDescription())).build();
        itemRepository.save(item);
        return new ItemDto();


    }
//        Item item = mapper.map(itemDto, Item.class);
//        item.setImage(fileService.uploadImage(itemDto.getImage()));
//        Item saveItem = itemRepository.save(item);
//        return mapper.map(saveItem, ItemDto.class);


        @Override
    public ItemDto updateItem(int itemId) {
        return new ItemDto();
    }

    @Override
    public void deleteItem(int itemId) {

    }
}
