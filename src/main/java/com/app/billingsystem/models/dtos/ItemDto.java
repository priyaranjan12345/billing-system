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
public class ItemDto {

    @NotNull(message = "name is required")
    @Size(min =1,max = 100,message = "name length should be 1 to 100")
    private  String name;

    @NotNull(message = "desc is required")
    @Size(min =50,max = 250,message = "desc length should be 1 to 100")
    private String desc;

    @NotNull(message = "price is required")
    private Double price;

    @NotNull(message = "image is required")
    private MultipartFile image;

}
