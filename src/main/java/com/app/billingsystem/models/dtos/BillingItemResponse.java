package com.app.billingsystem.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillingItemResponse {
    private Long id;
    String itemName;
    int itemQnt;
    double unitPrice;
}