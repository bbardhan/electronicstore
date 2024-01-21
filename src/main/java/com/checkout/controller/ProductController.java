package com.checkout.controller;

import com.checkout.dto.ProductDto;
import com.checkout.response.ProductResponse;
import com.checkout.service.ProductService;
import com.checkout.util.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductDto product) {
        productService.addProduct(product);
        ProductResponse response = ProductResponse.builder().message(MessageUtil.PRODUCT_CREATION_SUCCESSFUL).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int productId) {
        ProductDto product = productService.getProduct(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> removeProduct(@PathVariable int productId) {
        productService.removeProduct(productId);

        ProductResponse response = ProductResponse.builder().message(MessageUtil.PRODUCT_DELETION_SUCCESSFUL).build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
