package com.checkout.service;

import com.checkout.dto.AddToCartDto;
import com.checkout.dto.ProductDto;
import com.checkout.dto.ShoppingCartDto;
import com.checkout.model.BearerTokenWrapper;
import com.checkout.model.CartItem;
import com.checkout.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.checkout.util.AppUtil.modelMapper;
import static java.util.Objects.isNull;

@Service
@Slf4j
public class CartService {
    private AtomicInteger cartItemId;
    private Map<String, Map<Integer, CartItem>> cartItems;

    private ProductService productService;
    private BearerTokenWrapper bearerTokenWrapper;
    private JWTService jwtService;
    private CouponService couponService;

    public CartService(ProductService productService, BearerTokenWrapper bearerTokenWrapper, JWTService jwtService, CouponService couponService) {
        this.productService = productService;
        this.bearerTokenWrapper = bearerTokenWrapper;
        this.jwtService = jwtService;
        this.couponService = couponService;
        cartItemId = new AtomicInteger(1);
        cartItems = new ConcurrentHashMap<>();
    }

    public boolean addCartItem(final AddToCartDto addToCartDto) {
        String userName = getUserName();

        ProductDto productDto = productService.getProduct(addToCartDto.getProductId());
        Product product = modelMapper().map(productDto, Product.class);
        CartItem cartItem = CartItem.builder().id(cartItemId.getAndIncrement()).product(product).quantity(addToCartDto.getQuantity()).userName(userName).createdDate(new Date()).build();
        Map<Integer, CartItem> prodCartMap = cartItems.get(userName);

        if (isNull(prodCartMap)) {
            cartItems.computeIfAbsent(userName, (k) -> {
                Map<Integer, CartItem> cartMap = new ConcurrentHashMap<>();
                cartMap.put(product.getId(), cartItem);
                return cartMap;
            });
        } else {
            CartItem existingCartItem = prodCartMap.get(product.getId());
            if (isNull(existingCartItem)) {
                prodCartMap.put(product.getId(), cartItem);
            } else {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItem.getQuantity());
                prodCartMap.put(product.getId(), existingCartItem);
            }
        }

        log.info("New CartItem has been added for user ", userName);
        return true;
    }

    public boolean removeProductFromCart(int productId) {
        String userName = getUserName();
        Map<Integer, CartItem> prodCartMap = cartItems.get(userName);
        if (isNull(prodCartMap)) {
            log.info("CartItem having productId {} doesn't exist ", productId);
            return false;
        }
        CartItem existingCartItem = prodCartMap.remove(productId);
        if (existingCartItem != null) {
            log.info("CartItem having for the productId {} has been removed ", productId);
            return true;
        } else {
            log.info("CartItem having productId {} doesn't exist ", productId);
            return false;
        }
    }

    public ShoppingCartDto getCartItemsDetails() {
        String userName = getUserName();

        Map<Integer, CartItem> prodCartMap = cartItems.get(userName);
        if (isNull(prodCartMap)) {
            log.info("No CartItem exists for user {}", userName);
            return null;
        }

        List<CartItem> cartItems = prodCartMap.values().stream().toList();

        ShoppingCartDto shoppingCartDto = ShoppingCartDto.builder().cartItems(cartItems).couponsList(couponService.getAllCoupons()).build();
        shoppingCartDto.processCalculation();
        return shoppingCartDto;
    }

    public String getUserName() {
        String token = bearerTokenWrapper.getToken();
        return jwtService.getUsername(token);
    }
}
