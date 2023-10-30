package com.app.billingsystem.service;

import com.app.billingsystem.exception.NotFoundError;
import com.app.billingsystem.models.dtos.BillingDetailsResponse;
import com.app.billingsystem.models.dtos.BillingItemResponse;
import com.app.billingsystem.models.dtos.CartItemRequest;
import com.app.billingsystem.models.dtos.CartItemsRequest;
import com.app.billingsystem.models.entities.BillDetails;
import com.app.billingsystem.models.entities.BillItem;
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

    public BillingDetailsResponse saveBillingDetails(CartItemsRequest cartItemsRequest) {
        // current user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundError("Invalid User"));

        double grandTotal = 0.0;
        double discount = cartItemsRequest.getDiscount();
        double gstAmount = cartItemsRequest.getGstAmount();
        List<BillItem> billItems = new ArrayList<BillItem>();
        List<BillingItemResponse> billingItemResponses = new ArrayList<BillingItemResponse>();
        LocalDateTime billingDate = LocalDateTime.now();
        int noOfItems = cartItemsRequest.getCartItems().size();

        // generate invoice number
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long ts = timestamp.getTime();
        String invNum = "INV-" + ts;

        for (CartItemRequest cartItem : cartItemsRequest.getCartItems()) {
            Long itemId = cartItem.getItemId();
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundError("Item with " + itemId + " not found"));
            grandTotal += item.getPrice() * cartItem.getQuantity();

            BillItem billItem = BillItem.builder()
                    .itemQnt(cartItem.getQuantity())
                    .item(item)
                    .build();

            BillingItemResponse billingItemResponse = BillingItemResponse.builder()
                    .unitPrice(item.getPrice())
                    .itemName(item.getName())
                    .itemQnt(cartItem.getQuantity())
                    .build();

            billItems.add(billItem);
            billingItemResponses.add(billingItemResponse);
        }

        // save bill details
        BillDetails billingDetails = BillDetails.builder()
                .user(user)
                .billingDate(billingDate)
                .noOfItems(noOfItems)
                .invNo(invNum)
                .discount(discount)
                .gst(gstAmount)
                .grandTotal(grandTotal)
                .billItems(billItems)
                .build();
        BillDetails savedBillingDetails = billingDetailsRepository.save(billingDetails);

        // save bill items
        billItems.forEach(e -> e.setBillDetails(savedBillingDetails));
        billingItemRepository.saveAll(billItems);

        return BillingDetailsResponse.builder()
                .id(savedBillingDetails.getId())
                .billingDateTime(savedBillingDetails.getBillingDate())
                .invoiceNumber(savedBillingDetails.getInvNo())
                .noOfItems(savedBillingDetails.getNoOfItems())
                .discount(savedBillingDetails.getDiscount())
                .gst(savedBillingDetails.getGst())
                .grandTotal(savedBillingDetails.getGrandTotal())
                .items(billingItemResponses)
                .build();
    }

    public void getBillById(Long billId) {
        BillDetails billDetails = billingDetailsRepository.findById(billId).orElseThrow(() -> new NotFoundError("Item with " + billId + " not found"));
    }

    public void getAllBills() {
    }
}
