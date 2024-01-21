package com.checkout.controller;

import com.checkout.dto.AddToCartDto;
import com.checkout.dto.ShoppingCartDto;
import com.checkout.response.CartResponse;
import com.checkout.service.CartService;
import com.checkout.util.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts")
    public ResponseEntity<CartResponse> addCartItem(@RequestBody AddToCartDto addToCartDto) {
        cartService.addCartItem(addToCartDto);
        CartResponse response = CartResponse.builder().message(MessageUtil.CART_ITEM_ADDITION_SUCCESSFUL).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/carts")
    public ResponseEntity<ShoppingCartDto> getCartItemsDetails() {
        ShoppingCartDto shoppingCartDto = cartService.getCartItemsDetails();
        if (Objects.isNull(shoppingCartDto)) {
            return new ResponseEntity(MessageUtil.CART_IS_EMPTY, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(shoppingCartDto);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseEntity<CartResponse> removeProductFromCart(@PathVariable int productId) {
        boolean result = cartService.removeProductFromCart(productId);
        if (result) {
            CartResponse response = CartResponse.builder().message(MessageUtil.CART_ITEM_DELETION_SUCCESSFUL).build();
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(MessageUtil.PRODUCT_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }

}
