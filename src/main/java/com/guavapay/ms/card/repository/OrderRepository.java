package com.guavapay.ms.card.repository;

import com.guavapay.ms.card.entity.Order;
import com.guavapay.ms.card.type.CardType;
import com.guavapay.ms.card.type.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

    @Query("FROM Order o JOIN FETCH o.card c" +
            " WHERE o.userId = :userId" +
            " AND o.status = :status" +
            " AND c.type = :type")
    Optional<Order> findByUserIdAndStatusAndType(@Param(value = "userId") Long userId,
                                          @Param(value = "status") OrderStatus status,
                                          @Param(value = "type")CardType type);

    Optional<Order> findOrderByIdAndUserId(Long id, Long userId);

    @Modifying
    void deleteOrderById(Long id);
}
