package com.guavapay.ms.card.error;

public class AlreadySubmittedException extends CommonException {

    public AlreadySubmittedException() {
        super("Order already submitted");
    }
}
