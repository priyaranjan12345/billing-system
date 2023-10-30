package com.app.billingsystem.models.dtos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemsRequest {
    private List<CartItemRequest> cartItems;
    private Double discount;
    private Double gstAmount;
}
