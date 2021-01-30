package com.guavapay.ms.card.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guavapay.ms.card.repository.OrderRepository;
import com.guavapay.ms.card.security.CustomUserPrincipal;
import com.guavapay.ms.card.security.JwtToken;
import com.guavapay.ms.card.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static com.guavapay.ms.card.config.Constants.ORDER;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenProvider tokenProvider;

    @MockBean
    OrderRepository orderRepository;

    JwtToken jwtToken;

    @BeforeEach
    void setUp() {
        CustomUserPrincipal principal = new CustomUserPrincipal();
        principal.setId(1L);
        principal.setEmail("qtaleh41@gmail.com");
        principal.setFullName("Taleh Qurbanzada");
        principal.setUsername("username");
        principal.setMobile("mobile");
    }

    @Test
    void getOrders() throws Exception {
        given(orderRepository.findAllByUserId(1L)).willReturn(List.of(ORDER, ORDER));

        mockMvc.perform(get("/card/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtToken.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$..[?(@.id)]"));
    }
}