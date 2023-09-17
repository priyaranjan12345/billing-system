package com.app.billingsystem.controllers;
import com.app.billingsystem.helper.PageableResponse;
import com.app.billingsystem.models.dtos.CartItemsDto;

import com.app.billingsystem.models.dtos.ItemRequest;
import com.app.billingsystem.models.dtos.ItemResponse;
import com.app.billingsystem.service.ItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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


    @PostMapping(value = "/add-item", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ItemResponse> addItem(@Valid @ModelAttribute ItemRequest itemRequest) throws IOException {
        System.out.println("called add item controller");
        ItemResponse itemResponse = itemService.addItem(itemRequest);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);

    }


    @PutMapping(value = "/update-item-details/{id}")
    public ResponseEntity<ItemResponse> updateItemDetails(@PathVariable("id") Long id, @Valid @ModelAttribute ItemRequest itemRequest) throws Exception {
        ItemResponse itemResponse = itemService.updateItem(id, itemRequest);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/get-all-item")
    public  ResponseEntity<PageableResponse<ItemResponse>> getAllItem(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        return  new ResponseEntity<>(itemService.getAllItem(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }




    @DeleteMapping(value ="/delete-item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>("Item deleted!!",HttpStatus.OK);
    }



    @PostMapping(value = "/save-order-generate-bill")
    public String saveOrderGenerate(@RequestParam CartItemsDto cartItemsDto) {
        return "saveOrderGenerate";
    }

    @GetMapping(value = "/get-my-items")
    public List<ItemResponse> getMyItems() {
        return new ArrayList<>();
    }

    @GetMapping(value = "/get-my-order")
    public void getMyOrders() {
    }

    @GetMapping(value = "/get-order-by-id/{orderId}")
    public void getOrderDetailsById(@PathVariable BigInteger orderId) {
    }
}



