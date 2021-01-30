package com.guavapay.ms.card.controller;

import com.guavapay.ms.card.model.AccountDto;
import com.guavapay.ms.card.model.CardOrderDto;
import com.guavapay.ms.card.model.OrderDto;
import com.guavapay.ms.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/order")
    public CardOrderDto orderCard(@Valid @RequestBody CardOrderDto cardOrderDto) {
        return cardService.orderCard(cardOrderDto);
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrders() {
        return cardService.getOrders();
    }

    @GetMapping("/orders/{orderId}")
    public CardOrderDto getOrder(@NotNull @PathVariable("orderId") Long orderId) {
        return cardService.getOrder(orderId);
    }

    @PostMapping("/orders/{orderId}/submit")
    public AccountDto submitOrder(@NotNull @PathVariable("orderId") Long orderId) {
        return cardService.submitOrder(orderId);
    }

    @PutMapping("/orders/update")
    public CardOrderDto updateOrder(@Valid @RequestBody CardOrderDto cardOrderDto) {
        return cardService.updateOrder(cardOrderDto);
    }

    @DeleteMapping("/orders/{orderId}/delete")
    public void deleteOrder(@NotNull @PathVariable("orderId") Long orderId) {
        cardService.deleteOrder(orderId);
    }
}
