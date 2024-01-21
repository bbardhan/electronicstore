package com.checkout.processor;

import com.checkout.error.UnsupportedCouponTypeException;
import com.checkout.model.Coupon;

import static com.checkout.processor.CouponProcessor.CouponProcessorInput;

public interface CouponProcessorFactory {

    static CouponProcessor getCouponProcessor(Coupon coupon, CouponProcessorInput input) {
        switch (coupon.getCouponType()) {
            case FIXED -> {
                return new FixedCouponProcessor(input);
            }
            case PERCENT -> {
                return new PercentCouponProcessor(input);
            }
            case BUY_ONE_GET_FIFTY_PCT_OFF_FROM_SECOND -> {
                return new BuyOneGetFiftyPercentOffOnSecondCouponProcessor(input);
            }
            default -> {
                throw new UnsupportedCouponTypeException("Invalid couponType " + coupon.getCouponType());
            }
        }
    }
}