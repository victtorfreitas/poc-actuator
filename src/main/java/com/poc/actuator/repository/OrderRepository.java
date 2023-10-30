package com.poc.actuator.repository;

import com.poc.actuator.repository.entity.OrderEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<OrderEntity, ObjectId> {

    List<OrderEntity> findByRecipientNameContaining(String name);

    Optional<OrderEntity> findByRecipientNameAndEffected(String name, boolean status);

    List<OrderEntity> findAllByDateContainingAndEffected(String today, boolean isEffective);
}
