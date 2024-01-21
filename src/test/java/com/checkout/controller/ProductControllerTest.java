package com.checkout.controller;

import com.checkout.dto.ProductDto;
import com.checkout.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.checkout.util.TestUtil.getProductDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService service;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addProductAPI() throws Exception {

        ProductDto productDto = getProductDto();

        when(service.addProduct(productDto)).thenReturn(true);

        mockMvc.perform(post("/api/v1/products").content(mapper.writeValueAsString(productDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void getProductAPI() throws Exception {

        ProductDto productDto = getProductDto();

        when(service.getProduct(1)).thenReturn(productDto);

        mockMvc.perform(get("/api/v1/products/1"))
                .andDo(print())
                .andExpect(content().json(mapper.writeValueAsString(productDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void removeProductAPI() throws Exception {

        when(service.removeProduct(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/products/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
