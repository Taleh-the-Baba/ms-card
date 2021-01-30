package com.guavapay.ms.card.error;

public class InvalidCardTypeException extends CommonException {

    public InvalidCardTypeException() {
        super("Invalid card type");
    }
}
