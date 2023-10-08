package com.poc.actuator.config;

import com.poc.actuator.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class EndpointActuatorConfig {
    @Bean
    public CustomHealthEndPoint customHealthEndPoint(OrderRepository orderRepository) {
        return new CustomHealthEndPoint(orderRepository, new LinkedHashMap<>());
    }
}
