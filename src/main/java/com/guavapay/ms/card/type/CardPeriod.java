package com.guavapay.ms.card.type;

import com.guavapay.ms.card.error.InvalidCardPeriodException;

import java.util.Arrays;

public enum CardPeriod {

    A(12),
    B(24),
    C(36);

    private final int value;

    CardPeriod(int value) {
        this.value = value;
    }

    public static void validateCardPeriod(int period) {
        Arrays.stream(CardPeriod.values())
                .filter(cardPeriod -> cardPeriod.value == period)
                .findFirst()
                .orElseThrow(InvalidCardPeriodException::new);
    }
}
