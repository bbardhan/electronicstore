package com.checkout.error;

public class UnsupportedCouponTypeException extends RuntimeException {
    public UnsupportedCouponTypeException() {
    }

    public UnsupportedCouponTypeException(String message) {
        super(message);
    }
}
