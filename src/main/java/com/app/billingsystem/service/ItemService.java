package com.app.billingsystem.service;

import com.app.billingsystem.exception.NotFoundError;
import com.app.billingsystem.models.dtos.ItemRequest;
import com.app.billingsystem.models.dtos.ItemResponse;
import com.app.billingsystem.models.entities.Item;
import com.app.billingsystem.models.entities.User;
import com.app.billingsystem.repository.ItemRepository;
import com.app.billingsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {

    public ItemRepository itemRepository;
    public UserRepository userRepository;

    public FileService fileService;

    public ItemResponse addItem(ItemRequest itemRequest) throws Exception {
        LocalDateTime creationDate = LocalDateTime.now();
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        String filePath = fileService.uploadImage(itemRequest.getImage());

        // get user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> user = userRepository.findByEmail(username);

        // create item from request
        Item item = Item.builder()
                .name(itemRequest.getName())
                .description((itemRequest.getDescription()))
                .price(itemRequest.getPrice())
                .image(filePath)
                .creationDate(creationDate)
                .lastModifiedDate(lastUpdateDate)
                .user(user.orElse(null))
                .build();

        // save item
        Item savedItem = itemRepository.save(item);

        // return saved item in side response
        return ItemResponse.builder()
                .name(savedItem.getName())
                .description(savedItem.getDescription())
                .price(savedItem.getPrice())
                .image(savedItem.getImage())
                .creationDate(savedItem.getCreationDate().toString())
                .lastModifiedDate(savedItem.getLastModifiedDate().toString())
                .build();
    }


    public ItemResponse updateItem(Long itemId, ItemRequest itemRequest) throws Exception {
        Optional<Item> item = itemRepository.findById(itemId);
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        String filePath = fileService.uploadImage(itemRequest.getImage());

        // if item not found throw exception
        if (item.isEmpty()) {
           throw new NotFoundError("Item with "+itemId+" not found");
        }

        // update
        Item updatedItem = Item.builder()
                .name(itemRequest.getName())
                .description((itemRequest.getDescription()))
                .price(itemRequest.getPrice())
                .image(filePath)
                .lastModifiedDate(lastUpdateDate)
                .build();

        // save updated item
        Item savedItem = itemRepository.save(updatedItem);

        // return saved item in side response
        return ItemResponse.builder()
                .name(savedItem.getName())
                .description(savedItem.getDescription())
                .price(savedItem.getPrice())
                .image(savedItem.getImage())
                .creationDate(savedItem.getCreationDate().toString())
                .lastModifiedDate(savedItem.getLastModifiedDate().toString())
                .build();
    }


    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
