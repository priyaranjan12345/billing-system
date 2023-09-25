package com.app.billingsystem.service;

import com.app.billingsystem.exception.NotFoundError;
import com.app.billingsystem.exception.ResourceNotFoundException;
import com.app.billingsystem.helper.PageHelper;
import com.app.billingsystem.models.dtos.ItemRequest;
import com.app.billingsystem.models.dtos.ItemResponse;
import com.app.billingsystem.helper.PageableResponse;
import com.app.billingsystem.models.dtos.UpdateItemRequest;
import com.app.billingsystem.models.entities.Item;
import com.app.billingsystem.models.entities.User;
import com.app.billingsystem.repository.ItemRepository;
import com.app.billingsystem.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ItemService {
    final private ItemRepository itemRepository;
    final private UserRepository userRepository;
    final private FileService fileService;

    public ItemResponse addItem(ItemRequest itemRequest) throws IOException {
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
                .id(savedItem.getId())
                .name(savedItem.getName())
                .description(savedItem.getDescription())
                .price(savedItem.getPrice())
                .image(savedItem.getImage())
                .creationDate(savedItem.getCreationDate().toString())
                .lastModifiedDate(savedItem.getLastModifiedDate().toString())
                .build();
    }


    public ItemResponse updateItem(Long itemId, UpdateItemRequest updateItemRequest) throws IOException {
        LocalDateTime lastUpdateDate = LocalDateTime.now();

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundError("Item with " + itemId + " not found"));

        // update
        item.setName(updateItemRequest.getName());
        item.setDescription((updateItemRequest.getDescription()));
        item.setPrice(updateItemRequest.getPrice());
        if (updateItemRequest.getImage() != null) {
            String filePath = fileService.uploadImage(updateItemRequest.getImage());
            item.setImage(filePath);
        }
        item.setLastModifiedDate(lastUpdateDate);

        // save updated item
        Item savedItem = itemRepository.save(item);

        // return saved item in side response
        return ItemResponse.builder()
                .id(savedItem.getId())
                .name(savedItem.getName())
                .description(savedItem.getDescription())
                .price(savedItem.getPrice())
                .image(savedItem.getImage())
                .creationDate(savedItem.getCreationDate().toString())
                .lastModifiedDate(savedItem.getLastModifiedDate().toString())
                .build();
    }

    public PageableResponse<ItemResponse> getAllItem(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Item> page = itemRepository.findAll(pageable);
        return PageHelper.getPageableResponse(page, ItemResponse.class);
    }

    public void deleteItem(Long itemId) throws IOException {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item not found by this Id "));
        fileService.deleteFile(item.getImage());
        itemRepository.delete(item);
    }
}
