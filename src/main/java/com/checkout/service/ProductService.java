package com.checkout.service;

import com.checkout.error.ProductNotExistException;
import com.checkout.dto.ProductDto;
import com.checkout.model.Product;
import com.checkout.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.checkout.util.AppUtil.modelMapper;
import static java.util.Objects.isNull;


@Service
@Slf4j
public class ProductService {
    private AtomicInteger productID;
    private Map<Integer, Product> productMap;

    public ProductService() {
        productID = new AtomicInteger(1);
        productMap = new ConcurrentHashMap<>();
    }

    public boolean addProduct(ProductDto productDto) {
        Product product = modelMapper().map(productDto, Product.class);
        product.setId(productID.getAndIncrement());
        productMap.put(product.getId(), product);
        log.info("New Product having id {} has been created ", product.getId());
        return true;
    }

    public ProductDto getProduct(int productId) {
        Product product = productMap.get(productId);
        if (isNull(product)) {
            throw new ProductNotExistException(MessageUtil.PRODUCT_NOT_FOUND);
        }
        ProductDto productDto = modelMapper().map(product, ProductDto.class);

        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtoList = productMap.values()
                .stream()
                .map(product -> modelMapper().map(product, ProductDto.class))
                .collect(Collectors.toList());

        return productDtoList;
    }

    public boolean removeProduct(int productId) {
        Product product = productMap.remove(productId);
        if (isNull(product)) {
            log.error("Product having id {} doesn't exist ", productId);
            throw new ProductNotExistException(MessageUtil.COUPON_NOT_FOUND);
        }

        log.info("Product having id {} has been removed ", productId);
        return true;
    }
}
