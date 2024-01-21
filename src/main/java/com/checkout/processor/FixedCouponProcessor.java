package com.checkout.processor;

import com.checkout.model.Coupon;

public class FixedCouponProcessor implements CouponProcessor {

    private double cartTotalPrice;

    public FixedCouponProcessor(CouponProcessorInput input) {
        this.cartTotalPrice = input.getCartTotalPrice();
    }

    @Override
    public Result applyDiscount(Coupon coupon) {
        double totalDiscount = coupon.getCouponAmount();
        double totalPrice = cartTotalPrice - totalDiscount;
        return Result.builder().totalDiscount(totalDiscount).totalPrice(totalPrice).build();
    }
}
