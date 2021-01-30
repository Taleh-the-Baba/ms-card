package com.guavapay.ms.card.error;

public class AlreadyHaveCardTypeInPendingException extends CommonException {

    public AlreadyHaveCardTypeInPendingException() {
        super("User already ordered card with this type and not submitted yet");
    }
}
