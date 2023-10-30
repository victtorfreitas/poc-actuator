package com.poc.actuator.config;

import com.poc.actuator.exceptions.CustomEndPointNotFoundException;
import com.poc.actuator.repository.OrderRepository;
import com.poc.actuator.repository.entity.OrderEntity;
import com.poc.actuator.repository.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Slf4j
@AllArgsConstructor
@Component
@Endpoint(id = "order-process")
public class CustomHealthEndPoint {

    private final OrderRepository repository;

    @ReadOperation
    public CustomHealth ordersProcessing() {
        List<OrderEntity> orders = repository
                .findAllByDateContainingAndEffected(getToday(), false);

        Map<String, Status> details = orders
                .stream()
                .collect(Collectors.toMap(OrderEntity::getRecipientName, OrderEntity::getStatus));

        return getCustomHealth(details);
    }

    @WriteOperation
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alertFraud(@Selector String name) {
        var orderEntity = repository.findByRecipientNameAndEffected(name, false)
                .orElseThrow(CustomEndPointNotFoundException::new);
        orderEntity.setFraud(true);
        repository.save(orderEntity);
    }

    @DeleteOperation
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAlertFraud(@Selector String name) {
        var orderEntity = repository.findByRecipientNameAndEffected(name, false)
                .orElseThrow(CustomEndPointNotFoundException::new);
        orderEntity.setFraud(false);
        repository.save(orderEntity);
    }

    @ReadOperation
    public List<CustomHealth> customEndPointByName(@Selector String name) {
        return repository.findByRecipientNameContaining(name).stream()
                .map(CustomHealthEndPoint::getRecipientName)
                .map(CustomHealthEndPoint::getCustomHealth)
                .toList();
    }

    private static Map<String, Status> getRecipientName(OrderEntity orderEntity) {
        return Map.of(orderEntity.getRecipientName(), orderEntity.getStatus());
    }

    private static CustomHealth getCustomHealth(Map<String, Status> orderMapper) {
        return CustomHealth.builder().healthDetails(orderMapper).build();
    }

    private static String getToday() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }
}
