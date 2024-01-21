package com.checkout.util;

import com.checkout.dto.AddToCartDto;
import com.checkout.dto.CouponDto;
import com.checkout.dto.ProductDto;
import com.checkout.dto.ShoppingCartDto;
import com.checkout.model.CartItem;
import com.checkout.model.Coupon;
import com.checkout.model.CouponType;
import com.checkout.model.Product;

import java.util.Arrays;
import java.util.Date;

import static com.checkout.util.AppUtil.modelMapper;

public final class TestUtil {
    public final static int PRODUCT_ID = 1;
    public final static String USER_NAME = "user123";
    public final static int PRODUCT_QUANTITY = 20;
    public final static double PRODUCT_PRICE = 10.0;
    public final static double FIXED_DISCOUNT = 30.0;

    public static AddToCartDto addToCartDto() {
        AddToCartDto addToCartDto = AddToCartDto.builder().productId(PRODUCT_ID).quantity(PRODUCT_QUANTITY).build();
        return addToCartDto;
    }

    public static ShoppingCartDto getShoppingCartDto() {
        Product product = modelMapper().map(getProductDto(), Product.class);
        CartItem cartItem = CartItem.builder().id(1).product(product).quantity(addToCartDto().getQuantity()).userName(USER_NAME).build();
        ShoppingCartDto shoppingCartDto = ShoppingCartDto.builder().cartItems(Arrays.asList(cartItem)).couponsList(Arrays.asList((getCoupon()))).build();
        shoppingCartDto.processCalculation();
        return shoppingCartDto;
    }

    public static ProductDto getProductDto() {
        ProductDto productDto = ProductDto.builder().id(1).price(PRODUCT_PRICE).description("test").name("test").build();
        return productDto;
    }

    public static CouponDto getCouponDto() {
        CouponDto couponDto = CouponDto.builder().id(1).couponCode("fixed30").couponType(CouponType.FIXED).couponAmount(FIXED_DISCOUNT).build();
        return couponDto;
    }

    public static Coupon getCoupon() {
        Coupon coupon = modelMapper().map(getCouponDto(), Coupon.class);
        return coupon;
    }
}
