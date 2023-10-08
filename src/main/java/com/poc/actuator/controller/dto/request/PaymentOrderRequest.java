package com.poc.actuator.controller.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentOrderRequest {
    private BigDecimal value;
    private LocalDate date;
    private String recipientName;
}
