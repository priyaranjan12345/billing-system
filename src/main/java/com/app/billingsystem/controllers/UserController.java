package com.app.billingsystem.controllers;

import com.app.billingsystem.models.dtos.CartItemsDto;
import com.app.billingsystem.models.dtos.ItemRequest;
import com.app.billingsystem.models.dtos.ItemResponse;
import com.app.billingsystem.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/app/v1/user")
@AllArgsConstructor
public class UserController {
    final private ItemService itemService;
    @GetMapping("/get-user-data")
    public String getData() {
        return "Get: Hello User";
    }

    @PostMapping(value = "/add-item",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ItemResponse> addItem(@ModelAttribute ItemRequest itemRequest) throws IOException {
        ItemResponse itemResponse=itemService.addItem(itemRequest);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }
    @PostMapping(value = "/save-order-generate-bill")
    public String saveOrderGenerate(@RequestParam CartItemsDto cartItemsDto){
        return "saveOrderGenerate";
    }

    @GetMapping(value = "/get-my-items")
    public List<ItemResponse> getMyItems(){
        return new ArrayList<>();
    }
    @GetMapping(value = "/get-my-order")
    public void getMyOrders(){}

    @GetMapping(value = "/get-order-by-id/{orderId}")
    public void getOrderDetailsById(@PathVariable BigInteger orderId){}
}



