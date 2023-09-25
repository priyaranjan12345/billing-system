package com.app.billingsystem.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Builder
public class UpdateItemRequest {
    @NotNull(message = "name is required")
    @Size(min = 1, max = 100, message = "name length should be 1 to 100")
    private String name;

    @NotNull(message = "description is required")
    @Size(min = 50, max = 250, message = "description length should be 1 to 100")
    private String description;

    @NotNull(message = "price is required")
    private Double price;

    private MultipartFile image;
}