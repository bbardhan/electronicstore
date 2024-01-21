package com.checkout.service;

import com.checkout.dto.CouponDto;
import com.checkout.model.Coupon;
import com.checkout.model.CouponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.checkout.util.TestUtil.getCouponDto;


@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {

    private CouponService service;

    @BeforeEach
    public void setUp() {
        this.service = new CouponService();
    }

    @Test
    public void testAddCoupon() {
        boolean result = service.addCoupon(getCouponDto());
        Coupon coupon = service.getAllCoupons().get(0);

        assertAll(
                () -> assertEquals(true, result),
                () -> assertEquals(30, coupon.getCouponAmount()),
                () -> assertEquals("fixed30", coupon.getCouponCode()),
                () -> assertEquals(CouponType.FIXED, coupon.getCouponType())
        );
    }

    @Test
    public void testRemoveCoupon() {
        service.addCoupon(getCouponDto());
        CouponDto couponDto = service.getCoupon(1);
        service.removeCoupon(couponDto.getId());

        assertEquals(0, service.getAllCoupons().size());
    }
}
