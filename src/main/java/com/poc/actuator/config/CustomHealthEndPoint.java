package com.poc.actuator.config;

import com.poc.actuator.repository.OrderRepository;
import com.poc.actuator.repository.entity.Status;
import com.poc.actuator.repository.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Slf4j
@AllArgsConstructor
@Component
@Endpoint(id = "custom")
public class CustomHealthEndPoint {

    private final OrderRepository repository;

    @ReadOperation
    public CustomHealth health() {
        List<OrderEntity> orders = repository
                .findAllByDateContainingAndEffected(getToday(), false);

        Map<String, Status> details = orders
                .stream()
                .collect(Collectors
                        .toMap(OrderEntity::getRecipientName, OrderEntity::getStatus));

        return CustomHealth.builder()
                .healthDetails(details)
                .build();
    }

    private static String getToday() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }

    @ReadOperation
    public String customEndPointByName(@Selector String name) {
        return repository.existsByValue(new BigDecimal("15.5")) ? "UP" : "DOWN";
    }
}
