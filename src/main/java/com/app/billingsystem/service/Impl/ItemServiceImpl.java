package com.app.billingsystem.service.Impl;

import com.app.billingsystem.models.dtos.ItemDto;
import com.app.billingsystem.models.entities.Item;
import com.app.billingsystem.repository.ItemRepository;
import com.app.billingsystem.service.FileService;
import com.app.billingsystem.service.ItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    public ItemRepository itemRepository;

    public FileService fileService;

    private ModelMapper mapper;

    @Value("${item.image.path}")
    private  String imageUploadPath;


    @Override
    public ItemDto addItem(ItemDto itemDto) throws IOException {
        Item item = mapper.map(itemDto, Item.class);

        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setCreationDate(new Date());
        item.setLastModifiedDate(new Date());
        MultipartFile itemImage=itemDto.getImage();
        if(itemImage !=null && !itemImage.isEmpty()){
            String fileName=itemImage.getOriginalFilename();

        }



        Item saveItem = itemRepository.save(item);
        return mapper.map(saveItem, ItemDto.class);


    }



        @Override
    public ItemDto updateItem(int itemId) {
        return new ItemDto();
    }

    @Override
    public void deleteItem(int itemId) {

    }
}
