package com.guavapay.ms.card.entity.converter;

import com.guavapay.ms.card.type.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.Objects;

@Convert
public class OrderStatusConverter implements AttributeConverter<OrderStatus,String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        return Objects.nonNull(orderStatus) ? orderStatus.name().toLowerCase() : null;
    }

    @Override
    public OrderStatus convertToEntityAttribute(String value) {
        return Objects.nonNull(value) ? OrderStatus.of(value) : null;
    }
}
