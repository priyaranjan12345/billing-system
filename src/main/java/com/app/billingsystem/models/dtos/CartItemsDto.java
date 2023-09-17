package com.app.billingsystem.models.dtos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemsDto {
    private List<CartItem> itemsId;
}
