package com.checkout.model;

import com.checkout.error.UnsupportedCouponTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CouponType {
    FIXED("fixed"), PERCENT("percent"),BUY_ONE_GET_FIFTY_PCT_OFF_FROM_SECOND("buy1get50pcton2nd");

    private String value;

    CouponType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CouponType fromValue(String value) {
        for (CouponType b : CouponType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new UnsupportedCouponTypeException("Invalid couponType " + value);
    }
}
