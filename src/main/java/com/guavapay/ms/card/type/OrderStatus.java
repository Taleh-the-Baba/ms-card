package com.guavapay.ms.card.type;

import java.util.Arrays;

public enum OrderStatus {

    PENDING,
    SUBMITTED;

    public static OrderStatus of(String value) {
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }
}
