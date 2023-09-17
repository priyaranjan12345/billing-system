package com.app.billingsystem.models.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartItem {
    private  Long itemId;
    private  int quantity;
}
