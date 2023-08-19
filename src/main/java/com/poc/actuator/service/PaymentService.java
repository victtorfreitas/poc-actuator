package com.poc.actuator.service;

import com.poc.actuator.controller.dto.request.PaymentOrderRequest;

public interface PaymentService {

    void sendPayment(PaymentOrderRequest paymentOrderRequest);
}
