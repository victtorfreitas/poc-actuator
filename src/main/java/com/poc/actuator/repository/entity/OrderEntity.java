package com.poc.actuator.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("Order")
@ToString
public class OrderEntity {

    @Id
    @Field("_id")
    private String id;
    private BigDecimal value;
    private String date;
    private String recipientName;
    private boolean effected;

    public Status getStatus() {
        return this.isEffected() ? Status.PROCESSED : Status.PROCESSING;
    }
}
