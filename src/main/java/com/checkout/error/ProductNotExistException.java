package com.checkout.error;

public class ProductNotExistException extends RuntimeException {
    public ProductNotExistException() {
    }

    public ProductNotExistException(String message) {
        super(message);
    }
}
