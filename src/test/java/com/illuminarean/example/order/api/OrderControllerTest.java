package com.illuminarean.example.order.api;

import com.illuminarean.example.order.domain.Order;
import com.illuminarean.example.order.service.OrderService;
import com.illuminarean.example.security.Jwt;
import com.illuminarean.example.security.WithMockJwtAuthentication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    OrderControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockJwtAuthentication
    @DisplayName("주문 정보 조회 테스트(토큰이 올바른 경우)")
    void ordersTest() throws Exception {

        given(orderService.findByOrderList(1L))
                .willReturn(Collections.singletonList(new Order.Builder().userId(1L).build()));

        // When & Then
        mockMvc.perform(get("/api/orders/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("주문 정보 조회 테스트(토큰이 올바르지 않을 경우)")
    void ordersFailureTest()throws Exception{

        ResultActions result = mockMvc.perform(
                get("/api/orders/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Jwt.HEADER_KEY, "Bearer testasdfastoken")
                );
        result.andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockJwtAuthentication
    @DisplayName("주문 상세 조회 테스트(토큰이 올바른 경우)")
    void getOrderDetailTest() throws Exception {

        Order testOrder = new Order.Builder().userId(1L).build();
        given(orderService.findByOrderDetail(1L))
                .willReturn(testOrder);

        // When & Then
        mockMvc.perform(get("/api/orders/detail/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(testOrder.getUserId().intValue())));
    }

    @Test
    @DisplayName("주문 상세 조회 테스트(토큰이 올바르지 않을 경우)")
    void getOrderDetailFailureTest() throws Exception {

        // When & Then
        mockMvc.perform(get("/api/orders/detail/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Jwt.HEADER_KEY, "Bearer testasdfastoken"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockJwtAuthentication
    @DisplayName("주문 취소 테스트(토큰이 올바른 경우)")
    void cancelOrderTest() throws Exception {

        Order testOrder = new Order.Builder().userId(1L).build();
        given(orderService.cancelOrder(1L))
                .willReturn(true);

        // When & Then
        mockMvc.perform(get("/api/orders/cancel/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("주문 취소 테스트(토큰이 올바르지 않을 경우)")
    void cancelOrderFailureTest() throws Exception {

        // When & Then
        mockMvc.perform(get("/api/orders/cancel/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(Jwt.HEADER_KEY, "Bearer testasdfastoken"))
                .andExpect(status().is4xxClientError());
    }
}