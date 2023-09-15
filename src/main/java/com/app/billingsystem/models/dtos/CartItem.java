package com.app.billingsystem.models.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartItem {
    private  String itemId;
    private  int quantity;

}
