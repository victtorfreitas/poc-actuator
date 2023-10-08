package com.poc.actuator.service.mapper;

import com.poc.actuator.controller.dto.request.PaymentOrderRequest;
import com.poc.actuator.repository.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class PaymentMapper {

    public OrderEntity convertTo(PaymentOrderRequest paymentOrderRequest) {
        return OrderEntity.builder()
                .date(paymentOrderRequest.getDate().format(DateTimeFormatter.ISO_DATE))
                .recipientName(paymentOrderRequest.getRecipientName())
                .value(paymentOrderRequest.getValue())
                .build();
    }
}
