package com.udaan.leads.dto;

import com.udaan.leads.entity.order.Currency;
import lombok.Data;

@Data
public class SpecificRestaurantPerformanceInGroup {
    String restaurantId;
    String restaurantName;
    int totalOrders;
    int successOrders;
    int failedOrders;
    double successfulOrdersWorth;
    Currency currency;
}

