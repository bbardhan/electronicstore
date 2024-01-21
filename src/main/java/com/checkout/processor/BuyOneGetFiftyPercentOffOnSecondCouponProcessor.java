package com.checkout.processor;

import com.checkout.model.Coupon;

public class BuyOneGetFiftyPercentOffOnSecondCouponProcessor implements CouponProcessor {
    private double cartTotalPrice;
    private int prodQuantity;
    private double prodPrice;

    public BuyOneGetFiftyPercentOffOnSecondCouponProcessor(CouponProcessorInput input) {
        this.cartTotalPrice = input.getCartTotalPrice();
        this.prodQuantity = input.getProdQuantity();
        this.prodPrice = input.getProdPrice();
    }

    @Override
    public Result applyDiscount(Coupon coupon) {
        int prodQtyInTwoMultiple = prodQuantity/2;
        double totalDiscount = prodQtyInTwoMultiple * prodPrice / 2;
        double totalPrice = cartTotalPrice - totalDiscount;
        return Result.builder().totalDiscount(totalDiscount).totalPrice(totalPrice).build();
    }
}
