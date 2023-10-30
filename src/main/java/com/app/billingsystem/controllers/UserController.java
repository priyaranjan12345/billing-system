package com.app.billingsystem.controllers;

import com.app.billingsystem.helper.PageableResponse;
import com.app.billingsystem.models.dtos.*;

import com.app.billingsystem.service.BillingService;
import com.app.billingsystem.service.ItemService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

// user item controller, user bill controller,
// user profile controller
@RestController
@RequestMapping("/app/v1/user")
@AllArgsConstructor
public class UserController {
    final private ItemService itemService;
    final private BillingService billingService;

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
    public ResponseEntity<ItemResponse> updateItemDetails(@PathVariable("id") Long id, @Valid @ModelAttribute UpdateItemRequest updateItemRequest) throws Exception {
        ItemResponse itemResponse = itemService.updateItem(id, updateItemRequest);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/get-all-item")
    public ResponseEntity<PageableResponse<ItemResponse>> getAllItem(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return new ResponseEntity<>(itemService.getAllItem(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) throws IOException {
        itemService.deleteItem(id);
        return new ResponseEntity<>("Item deleted!!", HttpStatus.OK);
    }

    @PostMapping(value = "/save-order-generate-bill")
    public ResponseEntity<BillingDetailsResponse> saveOrderGenerateBill(@RequestBody CartItemsRequest cartItemsDto) {
        BillingDetailsResponse billingDetailsResponse = billingService.saveBillingDetails(cartItemsDto);
        return new ResponseEntity<>(billingDetailsResponse, HttpStatus.OK);
    }

    @GetMapping("/item-image/{file-name}")
    public void getItemImage(@PathVariable("file-name") String fileName, HttpServletResponse response) throws IOException {
        InputStream resource = itemService.getItemImage(fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @GetMapping(value = "/all-bills")
    public void getAllBills() {

    }

    @GetMapping(value = "/get-order-by-id/{orderId}")
    public void getBillDetailsById(@PathVariable Long orderId) {
    }
}



