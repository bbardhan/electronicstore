package com.checkout.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {
    private @NotNull Integer productId;
    private @NotNull Integer quantity;
}
