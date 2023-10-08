package com.poc.actuator.repository.entity;

import lombok.*;
import org.springframework.boot.actuate.endpoint.invoke.convert.IsoOffsetDateTimeConverter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

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
    private boolean effectived;
}
