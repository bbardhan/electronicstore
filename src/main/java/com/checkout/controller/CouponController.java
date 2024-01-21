package com.checkout.controller;

import com.checkout.dto.CouponDto;
import com.checkout.response.CouponResponse;
import com.checkout.service.CouponService;
import com.checkout.util.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CouponController {

    private CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/coupons")
    public ResponseEntity<CouponResponse> createCoupon(@Validated @RequestBody CouponDto couponDto) {
        couponService.addCoupon(couponDto);

        CouponResponse response = CouponResponse.builder().message(MessageUtil.COUPON_CREATION_SUCCESSFUL).build();
        return new ResponseEntity<CouponResponse>(response, HttpStatus.CREATED);
    }

    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<CouponDto> getCoupon(@PathVariable int couponId) {
        CouponDto coupon = couponService.getCoupon(couponId);
        return ResponseEntity.ok(coupon);
    }

    @DeleteMapping("/coupons/{couponId}")
    public ResponseEntity<CouponResponse> removeCoupon(@PathVariable int couponId) {
        couponService.removeCoupon(couponId);

        CouponResponse response = CouponResponse.builder().message(MessageUtil.COUPON_DELETION_SUCCESSFUL).build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}