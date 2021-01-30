package com.guavapay.ms.card.entity.converter;

import com.guavapay.ms.card.type.CardType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class CardTypeConverter implements AttributeConverter<CardType,String> {

    @Override
    public String convertToDatabaseColumn(CardType cardType) {
        return Objects.nonNull(cardType) ? cardType.name().toLowerCase() : null;
    }

    @Override
    public CardType convertToEntityAttribute(String value) {
        return Objects.nonNull(value) ? CardType.of(value) : null;
    }
}
