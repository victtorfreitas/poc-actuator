package com.poc.actuator.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("Order")
public class OrderEntity {

    @Id
    @Field("_id")
    private String id;
    private BigDecimal value;
    private LocalDateTime dateTime;
    private String recipientName;
}
