package com.app.billingsystem.service;

import com.app.billingsystem.exception.NotFoundError;
import com.app.billingsystem.models.dtos.CartItem;
import com.app.billingsystem.models.dtos.CartItemsDto;
import com.app.billingsystem.models.entities.BillingDetails;
import com.app.billingsystem.models.entities.Item;
import com.app.billingsystem.models.entities.User;
import com.app.billingsystem.repository.BillingDetailsRepository;
import com.app.billingsystem.repository.ItemRepository;
import com.app.billingsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BillingService {
    private final BillingDetailsRepository billingDetailsRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public void saveBillingDetails(CartItemsDto cartItemsDto) {
        // get user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new NotFoundError("Invalid User");
        }

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
        for (CartItem cartItem : cartItemsDto.getCartItems()) {
            Long itemId = cartItem.getItemId();
            Optional<Item> item = itemRepository.findById(itemId);
            if (item.isEmpty()) {
                throw new NotFoundError("Item with " + itemId + " not found");
            }
            grandTotal += item.get().getPrice();
        }

        // build billing details
        BillingDetails billingDetails = BillingDetails.builder()
                .user(user.orElse(null))
                .billingDate(currentDate)
                .noOfItems(noOfItems)
                .invNo(invNum)
                .discount(0.0)
                .gst(0.0)
                .grandTotal(grandTotal)
                .build();

        // save billing details
        BillingDetails savedBillingDetails = billingDetailsRepository.save(billingDetails);

        // return response
    }
}
