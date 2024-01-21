package com.checkout.processor;

import com.checkout.model.Coupon;
import lombok.Builder;
import lombok.Data;

public interface CouponProcessor {
    Result applyDiscount(Coupon coupon);

    @Data
    @Builder
    class Result {
        private double totalPrice;
        private double totalDiscount;
    }

    @Data
    @Builder
    class CouponProcessorInput {
        private double cartTotalPrice;
        private double prodPrice;
        private int prodQuantity;
    }
}
