package com.app.billingsystem.models.dtos;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginDto {
    private String email;
    private String password;
}
