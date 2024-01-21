package com.checkout.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UnsupportedCouponTypeException.class})
    public ResponseEntity<Object> handleCouponTypeException(UnsupportedCouponTypeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({UnsupportedProductTypeException.class})
    public ResponseEntity<Object> handleProductTypeException(UnsupportedProductTypeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({ProductNotExistException.class})
    public ResponseEntity<Object> handleProductNotExistException(ProductNotExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({CouponNotExistException.class})
    public ResponseEntity<Object> handleCouponNotExistException(CouponNotExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
