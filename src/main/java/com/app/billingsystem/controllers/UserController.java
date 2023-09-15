package com.app.billingsystem.controllers;

import com.app.billingsystem.models.dtos.CartItemsDto;
import com.app.billingsystem.models.dtos.ItemDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @GetMapping("/get-user-data")
    public String getData() {
        return "Get: Hello User";
    }

    @PostMapping(value = "/add-item",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addItem(@ModelAttribute ItemDto itemDto){
        return "Post: Hello User";
    }
    @PostMapping(value = "/save-order-generate-bill")
    public String saveOrderGenerate(@RequestParam CartItemsDto cartItemsDto){
        return "saveOrderGenerate";

    }

    @GetMapping(value = "/get-my-items")
    public List<ItemDto> getMyItems(){
        return new ArrayList<>();

    }
    @GetMapping(value = "/get-my-order")
    public void getMyOrders(){}

    @GetMapping(value = "/get-order-by-id/{orderId}")
    public void getOrderDetailsById(@PathVariable BigInteger orderId){}



}



