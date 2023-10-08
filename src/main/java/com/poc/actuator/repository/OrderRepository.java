package com.poc.actuator.repository;

import com.poc.actuator.repository.entity.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends MongoRepository<OrderEntity, String> {

    boolean existsByValue(BigDecimal value);

    @Query("{'date': {'$gte': ISODate('?0')}}")
    List<OrderEntity> findAllByDate(String today);
    List<OrderEntity> findAllByDateContainingAndEffectived(String today, boolean isEffective);

    List<OrderEntity> findAllByRecipientNameContains(String name);
}
