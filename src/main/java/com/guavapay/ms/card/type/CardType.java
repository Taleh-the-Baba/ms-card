package com.guavapay.ms.card.type;

import com.guavapay.ms.card.error.InvalidCardTypeException;

import java.util.Arrays;

public enum CardType {

    MC,
    VISA,
    UNION_PAY;

    public static CardType of(String value) {
        return Arrays.stream(CardType.values())
                .filter(cardType -> cardType.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

    public static void validateCardType(CardType type) {
        Arrays.stream(CardType.values())
                .filter(cardType -> cardType == type)
                .findFirst()
                .orElseThrow(InvalidCardTypeException::new);
    }
}
