package com.checkout.service;

import com.checkout.dto.ShoppingCartDto;
import com.checkout.model.BearerTokenWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.checkout.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    private CartService service;

    @Mock
    private ProductService productService;
    @Mock
    private BearerTokenWrapper wrapper;
    @Mock
    private JWTService jwtService;
    @Mock
    private CouponService couponService;

    @BeforeEach
    public void setUp() {
        this.service = new CartService(productService, wrapper, jwtService, couponService);
        when(productService.getProduct(PRODUCT_ID)).thenReturn(getProductDto());
        when(jwtService.getUsername(any())).thenReturn(USER_NAME);
        when(couponService.getAllCoupons()).thenReturn(Arrays.asList(getCoupon()));
    }

    @Test
    public void testAddCartItem() {
        boolean result = service.addCartItem(addToCartDto());

        ShoppingCartDto cartDto = service.getCartItemsDetails();

        double totalOriginalPrice = PRODUCT_QUANTITY * PRODUCT_PRICE;
        double totalPrice = totalOriginalPrice - FIXED_DISCOUNT;

        assertAll(
                () -> assertEquals(true, result),
                () -> assertEquals(totalOriginalPrice, cartDto.getTotalOriginalPrice()),
                () -> assertEquals(FIXED_DISCOUNT, cartDto.getTotalDiscount()),
                () -> assertEquals(totalPrice, cartDto.getTotalPrice())
        );
    }

    @Test
    public void testRemoveCartItem() {
        service.addCartItem(addToCartDto());
        ShoppingCartDto cartDto = service.getCartItemsDetails();

        assertEquals(1, cartDto.getCartItems().size());

        service.removeProductFromCart(cartDto.getCartItems().get(0).getProduct().getId());
        cartDto = service.getCartItemsDetails();

        assertEquals(0, cartDto.getCartItems().size());
    }

}
