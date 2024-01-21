package com.checkout.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ProductDto {
    private int id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private double price;
}
