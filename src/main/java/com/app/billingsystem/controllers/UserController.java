package com.app.billingsystem.controllers;

import com.app.billingsystem.models.dtos.CartItemsDto;
import com.app.billingsystem.models.dtos.ItemDto;
import com.app.billingsystem.service.Impl.ItemServiceImpl;
import com.app.billingsystem.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    public ResponseEntity<ItemDto> addItem(@ModelAttribute ItemDto itemDto){
        ItemDto itemDto1=itemService.addItem(itemDto);
        return new ResponseEntity<ItemDto>(itemDto1, HttpStatus.CREATED);


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



//    @PostMapping("/image/{userId}")
//    public ResponseEntity<ImageResponse> uploadUserImage(
//            @RequestParam("userImage")MultipartFile image, @PathVariable String userId) throws IOException {
//        String imageName = fileService.uploadImage(image, imageUploadPath);
//        UserDto user = userService.getUserById(userId);
//        user.setImageName(imageName);
//        UserDto userDto = userService.updateUser(user, userId);
//        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).message("success uploaded image").success(true).status(HttpStatus.CREATED).build();
//        return  new ResponseEntity<>(imageResponse,HttpStatus.OK);
//    }


}



