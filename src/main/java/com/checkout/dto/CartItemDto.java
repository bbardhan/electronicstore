package com.checkout.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CartItemDto {
    private Integer id;
    private @NotNull Integer productId;
    private @NotNull Integer quantity;
    private Date createdDate;
}
