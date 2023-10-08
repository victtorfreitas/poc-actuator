package com.poc.actuator.config;

import com.poc.actuator.repository.OrderRepository;
import com.poc.actuator.repository.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.cache.annotation.CacheEvict;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@AllArgsConstructor
@Endpoint(id = "custom")
public class CustomHealthEndPoint {

    private final OrderRepository repository;
    private final Map<String, Object> details;
    private final List<OrderEntity> orders = new ArrayList<>();

    @ReadOperation
    public CustomHealth health() {
        log.info("count: {}", orders.size());
        orders.removeIf(orderEntity -> true);
        orders.addAll(repository.findAllByDateContainingAndEffectived(LocalDate.now().format(DateTimeFormatter.ISO_DATE), false));
//        List<OrderEntity> orders = repository.findAllByRecipientNameContains("Pedro");
        log.info("receives {}", orders);
        orders.forEach(order -> details.put(order.getRecipientName(), order.isEffectived() ? "paymented" : "processing"));
        details.put("CustomHealthStatus", "Everything looks good");
//        details.put("count", 0);
        CustomHealth health = new CustomHealth();
        health.setHealthDetails(details);
        log.info("count: {}", orders.size());
        return health;
    }

    @ReadOperation
    public String customEndPointByName(@Selector String name) {
        return repository.existsByValue(new BigDecimal("15.5")) ? "UP" : "DOWN";
    }
}
