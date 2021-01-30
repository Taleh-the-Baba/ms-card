package com.guavapay.ms.card.service;

import com.guavapay.ms.card.error.AlreadyHaveCardTypeInPendingException;
import com.guavapay.ms.card.error.InvalidCardPeriodException;
import com.guavapay.ms.card.error.OrderNotFoundException;
import com.guavapay.ms.card.mapper.OrderMapperImpl;
import com.guavapay.ms.card.model.CardOrderDto;
import com.guavapay.ms.card.model.OrderDto;
import com.guavapay.ms.card.repository.OrderRepository;
import com.guavapay.ms.card.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.guavapay.ms.card.config.Constants.CARD_ORDER_DTO;
import static com.guavapay.ms.card.config.Constants.ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    OrderMapperImpl orderMapper;

    @Mock
    OrderRepository orderRepository;

    @Mock(lenient = true)
    TokenProvider tokenProvider;

    @InjectMocks
    CardService cardService;

    @BeforeEach
    void setUp() {
        given(tokenProvider.getUserId()).willReturn(1L);
    }

    @Test
    void orderCard() {
        //given
        given(orderRepository.findAllByUserId(1L)).willReturn(List.of());
        given(orderRepository.save(any())).willReturn(ORDER);

        //when
        CardOrderDto actual = cardService.orderCard(CARD_ORDER_DTO);

        //then
        assertThat(actual).isEqualTo(CARD_ORDER_DTO);
        assertThat(actual.getCodeword()).isEqualTo(ORDER.getCodeword());
        then(orderRepository).should(times(1)).findAllByUserId(1L);
        then(orderRepository).should(times(1)).save(any());
    }

    @Test
    void orderCard_Throws() {
        assertAll(
                () -> assertThrows(AlreadyHaveCardTypeInPendingException.class,
                        () -> {
                            given(orderRepository.findAllByUserId(1L)).willReturn(List.of(ORDER));
                            cardService.orderCard(CARD_ORDER_DTO);
                        }),
                () -> assertThrows(InvalidCardPeriodException.class,
                        () -> {
                            given(orderRepository.findAllByUserId(1L)).willReturn(List.of());
                            CARD_ORDER_DTO.getCard().setPeriod(15);
                            cardService.orderCard(CARD_ORDER_DTO);
                        })
        );
    }

    @Test
    void getOrders() {
        //given
        given(orderRepository.findAllByUserId(1L)).willReturn(List.of(ORDER, ORDER));

        //when
        List<OrderDto> actual = cardService.getOrders();

        //then
        assertThat(actual).hasSize(2);
    }

    @Test
    void getOrder() {
        //given
        given(orderRepository.findById(1L)).willReturn(Optional.of(ORDER));

        //when
        CardOrderDto actual = cardService.getOrder(1L);

        //then
        assertEquals(CARD_ORDER_DTO, actual);
    }

    @Test
    void getOrder_Throws() {
        given(orderRepository.findById(1L)).willReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class,
                () -> cardService.getOrder(1L));
    }

    @Test
    void submitOrder() {
    }

    @Test
    void updateOrder() {
    }

    @Test
    void deleteOrder() {
    }

    @Test
    void validateCardPeriod() {
    }

    @Test
    void validateCardType() {
    }
}