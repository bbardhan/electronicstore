package com.checkout.controller;

import com.checkout.dto.CouponDto;
import com.checkout.service.CouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.checkout.util.TestUtil.getCouponDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CouponControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CouponService service;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addCouponAPI() throws Exception {

        CouponDto couponDto = getCouponDto();

        when(service.addCoupon(couponDto)).thenReturn(true);

        mockMvc.perform(post("/api/v1/coupons").content(mapper.writeValueAsString(couponDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void getCouponAPI() throws Exception {

        CouponDto couponDto = getCouponDto();

        when(service.getCoupon(1)).thenReturn(couponDto);

        mockMvc.perform(get("/api/v1/coupons/1"))
                .andDo(print())
                .andExpect(content().json(mapper.writeValueAsString(couponDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void removeCouponAPI() throws Exception {

        when(service.removeCoupon(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/coupons/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
