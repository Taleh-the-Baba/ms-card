package com.guavapay.ms.card.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {

    private java.lang.String accountNumber;
    private java.lang.String cardNumber;
}
