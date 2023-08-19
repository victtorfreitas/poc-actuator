package com.poc.actuator.controller.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentOrderRequest {
    private BigDecimal value;
    private LocalDateTime dateTime;
    private String recipientName;
}
