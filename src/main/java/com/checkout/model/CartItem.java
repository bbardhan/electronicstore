package com.checkout.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CartItem {
    private Integer id;
    private @NotNull Product product;
    private @NotNull Integer quantity;
    private Date createdDate;
    private String userName;
}
