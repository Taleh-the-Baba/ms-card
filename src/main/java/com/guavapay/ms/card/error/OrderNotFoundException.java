package com.guavapay.ms.card.error;

public class OrderNotFoundException extends CommonException {

    public OrderNotFoundException() {
        super("Order not found");
    }
}
