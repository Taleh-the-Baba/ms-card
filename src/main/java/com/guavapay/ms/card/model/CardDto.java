package com.guavapay.ms.card.model;

import com.guavapay.ms.card.type.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    @NotEmpty
    private CardType type;
    private String holder;

    @NotNull
    private Integer period;
}
