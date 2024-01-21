package com.checkout.processor;

import com.checkout.model.Coupon;

public class PercentCouponProcessor implements CouponProcessor {

    private double cartTotalPrice;

    public PercentCouponProcessor(CouponProcessorInput input) {
        this.cartTotalPrice = input.getCartTotalPrice();
    }

    @Override
    public Result applyDiscount(Coupon coupon) {
        double totalDiscount = cartTotalPrice * coupon.getCouponAmount() / 100;
        double totalPrice = cartTotalPrice - totalDiscount;
        return Result.builder().totalDiscount(totalDiscount).totalPrice(totalPrice).build();
    }
}
