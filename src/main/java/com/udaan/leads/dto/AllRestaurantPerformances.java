package com.udaan.leads.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class AllRestaurantPerformances
{
    Instant start;
    Instant end;

    int totalOrders;
    int successfulOrders;
    int failedOrders;

    List<SpecificRestaurantPerformanceInGroup> restaurants;
}
