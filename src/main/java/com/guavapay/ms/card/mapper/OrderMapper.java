package com.guavapay.ms.card.mapper;

import com.guavapay.ms.card.entity.Card;
import com.guavapay.ms.card.entity.Order;
import com.guavapay.ms.card.model.CardDto;
import com.guavapay.ms.card.model.CardOrderDto;
import com.guavapay.ms.card.model.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper {

    public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "status", expression = "java(com.guavapay.ms.card.type.OrderStatus.PENDING)")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract Order toOrder(CardOrderDto cardOrderDto);

    public abstract Card toCard(CardDto cardDto);

    public abstract OrderDto toOrderDto(Order order);

    public abstract CardOrderDto toCardOrderDto(Order order);

    public abstract CardDto toCardDto(Card card);
}
