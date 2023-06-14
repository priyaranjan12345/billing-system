package com.app.billingsystem.models.dtos;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType;
}
