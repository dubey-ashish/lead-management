package com.udaan.leads.dto;

import com.udaan.leads.entity.order.Currency;
import lombok.Data;

import java.time.Instant;

// [Total - Successful - Failed - Total value of Successful Orders] of a restaurant

@Data
public class SpecificRestaurantPerformance
{
    Instant start;
    Instant end;

    String restaurantId;
    String restaurantName;
    int totalOrders;
    int successOrders;
    int failedOrders;
    double successfulOrdersWorth;
    Currency currency;
}
