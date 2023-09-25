package com.app.billingsystem.service;

import com.app.billingsystem.exception.NotFoundError;
import com.app.billingsystem.models.dtos.BillingDetailsResponse;
import com.app.billingsystem.models.dtos.BillingItemResponse;
import com.app.billingsystem.models.dtos.CartItem;
import com.app.billingsystem.models.dtos.CartItemsDto;
import com.app.billingsystem.models.entities.BillingDetails;
import com.app.billingsystem.models.entities.BillingItem;
import com.app.billingsystem.models.entities.Item;
import com.app.billingsystem.models.entities.User;
import com.app.billingsystem.repository.BillingDetailsRepository;
import com.app.billingsystem.repository.BillingItemRepository;
import com.app.billingsystem.repository.ItemRepository;
import com.app.billingsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BillingService {
    final private BillingDetailsRepository billingDetailsRepository;
    final private BillingItemRepository billingItemRepository;
    final private UserRepository userRepository;
    final private ItemRepository itemRepository;

    public BillingDetailsResponse saveBillingDetails(CartItemsDto cartItemsDto) {
        // get user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundError("Invalid User"));

        // billing date
        LocalDateTime currentDate = LocalDateTime.now();

        // no of items
        int noOfItems = cartItemsDto.getCartItems().size();

        // generate invoice number
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long ts = timestamp.getTime();
        String invNum = "INV-" + ts;

        // calculate grand total
        double grandTotal = 0.0;
        List<Item> allItems = new ArrayList<>();

        for (CartItem cartItem : cartItemsDto.getCartItems()) {
            Long itemId = cartItem.getItemId();
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundError("Item with " + itemId + " not found"));
            grandTotal += item.getPrice() * cartItem.getQuantity();

            allItems.add(item);
        }

        // build billing details
        BillingDetails billingDetails = BillingDetails.builder()
                .user(user)
                .billingDate(currentDate)
                .noOfItems(noOfItems)
                .invNo(invNum)
                .discount(0.0)
                .gst(0.0)
                .grandTotal(grandTotal)
                .build();

        // save billing details
        BillingDetails savedBillingDetails = billingDetailsRepository.save(billingDetails);

        List<BillingItem> billingItems = new ArrayList<>();
        List<BillingItemResponse> itemResponses = new ArrayList<>();

        // arrange item
        for (Item item : allItems) {
            BillingItem billingItem = BillingItem.builder()
                    .item(item)
                    .itemQnt(noOfItems)
                    .billingDetails(savedBillingDetails)
                    .build();

            BillingItemResponse billingItemResponse = BillingItemResponse.builder()
                    .id(item.getId())
                    .itemName(item.getName())
                    .itemQnt(noOfItems)
                    .unitPrice(item.getPrice())
                    .build();

            billingItems.add(billingItem);
            itemResponses.add(billingItemResponse);
        }

        // save all billing items
        billingItemRepository.saveAll(billingItems);

        // return response
        return BillingDetailsResponse.builder()
                .id(savedBillingDetails.getId())
                .billingDateTime(savedBillingDetails.getBillingDate())
                .invoiceNumber(savedBillingDetails.getInvNo())
                .noOfItems(savedBillingDetails.getNoOfItems())
                .discount(savedBillingDetails.getDiscount())
                .gst(savedBillingDetails.getGst())
                .grandTotal(savedBillingDetails.getGrandTotal())
                .items(itemResponses)
                .build();
    }

    public void getBills() {

    }
}
