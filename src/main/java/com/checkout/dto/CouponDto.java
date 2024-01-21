package com.checkout.dto;

import com.checkout.model.CouponType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto {
    private int id;
    @NotBlank
    private String couponCode;
    @NotNull
    private CouponType couponType;
    @NotNull
    @PositiveOrZero
    private double couponAmount;
}
