package com.app.billingsystem.models.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.xml.stream.XMLInputFactory;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthRequest {
    @NotNull
    @Email(message = "Invalid email")
    private String email;

    @NotNull
    @Size(min=2, max=10, message = "Password should be grater than 2 character and lesser than 10 character")
    private String password;
}
