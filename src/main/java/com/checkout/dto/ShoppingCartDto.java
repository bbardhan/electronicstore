package com.checkout.dto;

import com.checkout.model.CartItem;
import com.checkout.model.Coupon;
import com.checkout.processor.CouponProcessor;
import com.checkout.processor.CouponProcessorFactory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static com.checkout.processor.CouponProcessor.CouponProcessorInput;
import static com.checkout.processor.CouponProcessor.Result;

@Data
@Builder
public class ShoppingCartDto {
    private List<CartItem> cartItems;
    private List<Coupon> couponsList;
    private double totalOriginalPrice;
    private double totalDiscount;
    private double totalPrice;

    public void processCalculation() {
        double cartTotalPrice = 0;

        totalOriginalPrice = cartItems.stream().map(cItem -> cItem.getProduct().getPrice() * cItem.getQuantity())
                .mapToDouble(Double::doubleValue).sum();

        for (CartItem cartItem : cartItems) {
            cartTotalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity();

            for (Coupon coupon : couponsList) {
                CouponProcessorInput input = CouponProcessorInput.builder().prodPrice(cartItem.getProduct().getPrice()).prodQuantity(cartItem.getQuantity()).cartTotalPrice(cartTotalPrice).build();
                CouponProcessor couponProcessor = CouponProcessorFactory.getCouponProcessor(coupon, input);
                Result result = couponProcessor.applyDiscount(coupon);

                totalDiscount += result.getTotalDiscount();
                cartTotalPrice = result.getTotalPrice();
            }
            totalPrice += cartTotalPrice;
        }
    }
}