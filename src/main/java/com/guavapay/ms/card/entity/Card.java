package com.guavapay.ms.card.entity;

import com.guavapay.ms.card.entity.converter.CardTypeConverter;
import com.guavapay.ms.card.type.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "card", schema = "public")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CardTypeConverter.class)
    @Column(nullable = false)
    private CardType type;

    @Column(nullable = false)
    private String holder;

    @Column(nullable = false)
    private Integer period;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "account_number")
    private String accountNumber;
}
