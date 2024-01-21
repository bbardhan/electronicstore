package com.checkout.error;

public class UnsupportedProductTypeException extends RuntimeException {
    public UnsupportedProductTypeException() {
    }

    public UnsupportedProductTypeException(String message) {
        super(message);
    }
}
