package com.poc.actuator.controller;

import com.poc.actuator.controller.dto.request.PaymentOrderRequest;
import com.poc.actuator.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody PaymentOrderRequest paymentOrderRequest) {
        paymentService.sendPayment(paymentOrderRequest);
    }
}
