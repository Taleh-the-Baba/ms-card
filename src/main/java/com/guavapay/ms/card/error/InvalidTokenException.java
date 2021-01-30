package com.guavapay.ms.card.error;

public class InvalidTokenException extends CommonException {

    public InvalidTokenException() {
        super("Invalid token");
    }
}
