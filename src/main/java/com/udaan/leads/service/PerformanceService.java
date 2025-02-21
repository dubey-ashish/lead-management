package com.udaan.leads.service;

import com.udaan.leads.dto.AllRestaurantPerformances;
import com.udaan.leads.dto.SpecificRestaurantPerformance;
import com.udaan.leads.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class PerformanceService
{
    @Autowired
    PerformanceRepository performanceRepository;

    // Get performance of a specific restaurant
    public Optional<SpecificRestaurantPerformance> restaurantPerformanceById(String id, Instant start, Instant end)
    {
        return Optional.of(performanceRepository.restaurantPerformanceById(id,start,end));
    }

    // Get performance of all restaurants sorted in decreasing order of successful orders per restaurant
    public Optional<AllRestaurantPerformances> allRestaurantPerformances(Instant start, Instant end)
    {
        return Optional.of(performanceRepository.allRestaurantPerformances(start,end));
    }
}
