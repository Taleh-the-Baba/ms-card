package com.guavapay.ms.card.error;

public class CommonException extends RuntimeException {

    private String errorMessage;

    public CommonException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
