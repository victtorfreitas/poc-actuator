package com.poc.actuator.service.impl;

import com.poc.actuator.controller.dto.request.PaymentOrderRequest;
import com.poc.actuator.repository.OrderRepository;
import com.poc.actuator.service.PaymentService;
import com.poc.actuator.service.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @SneakyThrows
    @Override
    public void sendPayment(PaymentOrderRequest paymentOrderRequest) {
        log.info("Payment sending");
        orderRepository.save(paymentMapper.convertTo(paymentOrderRequest));
        log.info("Payment sent {}", paymentOrderRequest);
    }
}
