package com.guavapay.ms.card.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardOrderDto {

    private CardDto card;
    @NotNull
    private String codeword;
    private Boolean urgent;
}
