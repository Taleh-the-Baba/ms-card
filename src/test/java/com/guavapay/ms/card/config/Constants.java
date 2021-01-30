package com.guavapay.ms.card.config;

import com.guavapay.ms.card.entity.Card;
import com.guavapay.ms.card.entity.Order;
import com.guavapay.ms.card.model.CardDto;
import com.guavapay.ms.card.model.CardOrderDto;
import com.guavapay.ms.card.model.OrderDto;
import com.guavapay.ms.card.type.CardType;
import com.guavapay.ms.card.type.OrderStatus;

import java.time.LocalDateTime;

public interface Constants {

    LocalDateTime createDate = LocalDateTime.of(2021, 1, 30, 0, 0, 0);

    Card CARD = Card.builder()
            .id(1L)
                .type(CardType.VISA)
                .holder("Taleh Qurbanzada")
                .period(12)
                .cardNumber("1111222233334444")
                .accountNumber("qwerty")
                .build();

    Order ORDER = Order.builder()
            .id(1L)
                .codeword("codeword")
                .status(OrderStatus.PENDING)
                .createDate(createDate)
                .userId(1L)
                .urgent(Boolean.TRUE)
                .card(CARD)
                .build();

    CardDto CARD_DTO = CardDto.builder()
            .holder("Taleh Qurbanzada")
                .period(12)
                .type(CardType.VISA)
                .build();

    CardOrderDto CARD_ORDER_DTO = CardOrderDto.builder()
            .codeword("codeword")
                .urgent(Boolean.TRUE)
                .card(CARD_DTO)
                .build();

    OrderDto ORDER_DTO = OrderDto.builder()
            .id(1L)
                .createDate(createDate)
                .status(OrderStatus.PENDING)
                .build();
}
