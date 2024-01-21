package com.checkout.error;

public class CouponNotExistException extends RuntimeException {
    public CouponNotExistException() {
    }

    public CouponNotExistException(String message) {
        super(message);
    }
}
