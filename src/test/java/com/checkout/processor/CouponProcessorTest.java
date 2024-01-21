package com.checkout.processor;

import com.checkout.model.Coupon;
import com.checkout.model.CouponType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.checkout.processor.CouponProcessor.CouponProcessorInput;
import static com.checkout.processor.CouponProcessor.Result;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CouponProcessorTest {

    @Test
    public void testFixedDiscount() {
        Coupon coupon = Coupon.builder().couponCode("fixed10").couponType(CouponType.FIXED).couponAmount(10).build();

        CouponProcessor.CouponProcessorInput input = CouponProcessorInput.builder().cartTotalPrice(200).build();
        CouponProcessor couponProcessor = CouponProcessorFactory.getCouponProcessor(coupon, input);
        Result result = couponProcessor.applyDiscount(coupon);

        assertEquals(10, result.getTotalDiscount());
        assertEquals(190, result.getTotalPrice());
    }

    @Test
    public void testPercentageDiscount() {
        Coupon coupon = Coupon.builder().couponCode("percent10").couponType(CouponType.PERCENT).couponAmount(10).build();

        CouponProcessor.CouponProcessorInput input = CouponProcessorInput.builder().cartTotalPrice(200).build();
        CouponProcessor couponProcessor = CouponProcessorFactory.getCouponProcessor(coupon, input);
        Result result = couponProcessor.applyDiscount(coupon);

        assertEquals(20, result.getTotalDiscount());
        assertEquals(180, result.getTotalPrice());
    }

    @Test
    public void testBuyOneGetFiftyPercentOffOnSecondDiscount() {
        Coupon coupon = Coupon.builder().couponCode("buy1get50pcton2nd").couponType(CouponType.BUY_ONE_GET_FIFTY_PCT_OFF_FROM_SECOND).build();

        CouponProcessorInput input = CouponProcessorInput.builder().cartTotalPrice(200).prodQuantity(10).prodPrice(20).build();
        CouponProcessor couponProcessor = CouponProcessorFactory.getCouponProcessor(coupon, input);
        Result result = couponProcessor.applyDiscount(coupon);

        assertEquals(50, result.getTotalDiscount());
        assertEquals(150, result.getTotalPrice());
    }
}
