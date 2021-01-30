package com.guavapay.ms.card.service;

import com.guavapay.ms.card.entity.Order;
import com.guavapay.ms.card.error.AlreadyHaveCardTypeInPendingException;
import com.guavapay.ms.card.error.AlreadySubmittedException;
import com.guavapay.ms.card.error.OrderNotFoundException;
import com.guavapay.ms.card.mapper.OrderMapper;
import com.guavapay.ms.card.model.AccountDto;
import com.guavapay.ms.card.model.CardOrderDto;
import com.guavapay.ms.card.model.OrderDto;
import com.guavapay.ms.card.repository.OrderRepository;
import com.guavapay.ms.card.security.TokenProvider;
import com.guavapay.ms.card.type.CardPeriod;
import com.guavapay.ms.card.type.CardType;
import com.guavapay.ms.card.type.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.guavapay.ms.card.util.CardUtils.buildRandomCardNumber;

@Service
@RequiredArgsConstructor
public class CardService {

    private final OrderRepository orderRepository;
    private final TokenProvider tokenProvider;
    private final OrderMapper orderMapper = OrderMapper.INSTANCE;


    /**
     * Check if user have one card with the same type in PENDING status.
     * Validate period(12, 24, 36) and type.
     * Create order.
     * @param cardOrderDto for creating.
     * @return CardOrderDto.
     */
    public CardOrderDto orderCard(CardOrderDto cardOrderDto) {

        checkIfUserHaveOnePendingCardByType(cardOrderDto);
        CardPeriod.validateCardPeriod(cardOrderDto.getCard().getPeriod());
        CardType.validateCardType(cardOrderDto.getCard().getType());

        Order order = orderMapper.toOrder(cardOrderDto);
        order.setUserId(tokenProvider.getUserId());
        orderRepository.save(order);

        return cardOrderDto;
    }


    /**
     * User can have only one card with the same type in PENDING status at a time.
     * @param cardOrderDto for validation.
     */
    private void checkIfUserHaveOnePendingCardByType(CardOrderDto cardOrderDto) {
        var orderOpt = orderRepository.findAllByUserId(tokenProvider.getUserId())
                .stream()
                .filter(order -> order.getCard().getType() == cardOrderDto.getCard().getType())
                .filter(order -> order.getStatus() == OrderStatus.PENDING)
                .findFirst();

        if (orderOpt.isPresent())
            throw new AlreadyHaveCardTypeInPendingException();
    }

    public List<OrderDto> getOrders() {
        Long userId = tokenProvider.getUserId();
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    public CardOrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toCardOrderDto)
                .orElseThrow(OrderNotFoundException::new);
    }

    /**
     * If order belongs to current user, submit order.
     * @param orderId for submitting.
     * @return AccountDto.
     */
    public AccountDto submitOrder(Long orderId) {
        Order order = orderRepository.findOrderByIdAndUserId(orderId, tokenProvider.getUserId())
                .orElseThrow(OrderNotFoundException::new);

        final String cardNumber = buildRandomCardNumber().toString();
        final String accountNumber = UUID.randomUUID().toString();

        order.setStatus(OrderStatus.SUBMITTED);
        order.getCard().setCardNumber(cardNumber);
        order.getCard().setAccountNumber(accountNumber);

        orderRepository.save(order);

        return AccountDto.builder()
                .cardNumber(cardNumber)
                .accountNumber(accountNumber)
                .build();
    }

    /**
     * Check if current user have order by order status PENDING and card type.
     * Save updated order.
     * @param cardOrderDto for updating.
     * @return CardOrderDto.
     */
    public CardOrderDto updateOrder(CardOrderDto cardOrderDto) {
        orderRepository.findByUserIdAndStatusAndType(tokenProvider.getUserId(),
                OrderStatus.PENDING,
                cardOrderDto.getCard().getType())
                .orElseThrow(AlreadySubmittedException::new);

        Order order = orderMapper.toOrder(cardOrderDto);
        orderRepository.save(order);
        return cardOrderDto;
    }

    /**
     * Check if this order belongs to current user.
     * Delete order by id.
     * @param orderId for deleting.
     */
    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.findOrderByIdAndUserId(orderId, tokenProvider.getUserId())
                .orElseThrow(OrderNotFoundException::new);
        orderRepository.deleteOrderById(orderId);
    }
}
