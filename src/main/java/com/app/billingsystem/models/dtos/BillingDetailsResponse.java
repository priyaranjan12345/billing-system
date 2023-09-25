package com.app.billingsystem.models.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.time.LocalDateTime;

@Data
@Builder
public class BillingDetailsResponse {
    private Long id;
    private String invoiceNumber;
    private LocalDateTime billingDateTime;
    private double discount;
    private double gst;
    private double grandTotal;
    private int noOfItems;
    private List<BillingItemResponse> items;
}