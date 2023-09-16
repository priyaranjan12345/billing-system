package com.app.billingsystem.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private String name;
    private String description;
    private Double price;
    private String image;
    private String creationDate;
    private String lastModifiedDate;
}
