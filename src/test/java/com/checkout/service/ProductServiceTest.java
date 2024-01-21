package com.checkout.service;

import com.checkout.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.checkout.util.TestUtil.getProductDto;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private ProductService service;

    @BeforeEach
    public void setUp() {
        this.service = new ProductService();
    }

    @Test
    public void testAddProduct() {
        boolean result = service.addProduct(getProductDto());
        ProductDto productDto = service.getProduct(1);

        assertAll(
                () -> assertEquals(true, result),
                () -> assertEquals(10, productDto.getPrice()),
                () -> assertEquals("test", productDto.getName())
        );
    }

    @Test
    public void testRemoveProduct() {
        service.addProduct(getProductDto());
        ProductDto productDto = service.getAllProducts().get(0);

        service.removeProduct(productDto.getId());

        assertEquals(0, service.getAllProducts().size());
    }
}
