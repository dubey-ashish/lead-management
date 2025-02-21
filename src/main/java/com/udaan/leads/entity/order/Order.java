package com.udaan.leads.entity.order;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    @Indexed(unique = true)
    private String correlationId;

    private String restaurantId;
    private String restaurantName;

    private List<Items> items;
    private Instant timeStamp;
    private Currency currency;

    private OrderStatus orderStatus;
    private FailedSubtypes failedSubtype;   //enum NULL if order successful
}

