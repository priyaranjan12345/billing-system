package com.app.billingsystem.models.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartItemRequest {
    private  Long itemId;
    private  int quantity;
}
