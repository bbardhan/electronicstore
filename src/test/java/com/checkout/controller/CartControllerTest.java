package com.checkout.controller;

import com.checkout.dto.AddToCartDto;
import com.checkout.dto.ShoppingCartDto;
import com.checkout.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.checkout.util.TestUtil.addToCartDto;
import static com.checkout.util.TestUtil.getShoppingCartDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartService service;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addCartItemAPI() throws Exception {

        AddToCartDto addToCartDto = addToCartDto();

        when(service.addCartItem(addToCartDto)).thenReturn(true);

        mockMvc.perform(post("/api/v1/carts").content(mapper.writeValueAsString(addToCartDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void getCartItemsAPI() throws Exception {

        ShoppingCartDto shoppingCartDto = getShoppingCartDto();


        when(service.getCartItemsDetails()).thenReturn(shoppingCartDto);

        mockMvc.perform(get("/api/v1/carts"))
                .andDo(print())
                .andExpect(content().json(mapper.writeValueAsString(shoppingCartDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void removeProductFromCartItemAPI() throws Exception {

        when(service.removeProductFromCart(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/carts/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
