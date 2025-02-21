package com.udaan.leads.controller;

import com.udaan.leads.dto.AllRestaurantPerformances;
import com.udaan.leads.dto.SpecificRestaurantPerformance;
import com.udaan.leads.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("v1/performance")
public class PerformanceController
{
    @Autowired
    PerformanceService performanceService;

    // Get performance of a specific restaurant withing a specific period

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<SpecificRestaurantPerformance> restaurantPerformanceById(@PathVariable("id") String id, @RequestParam("start") Instant start, @RequestParam("end") Instant end)
    {
        Optional<SpecificRestaurantPerformance> restaurantPerformance=performanceService.restaurantPerformanceById(id,start,end);

        if(restaurantPerformance.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(restaurantPerformance.get());

    }

    // Get performance of all restaurants sorted in decreasing order of successful orders per restaurant within a specific period

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/restaurants")
    public ResponseEntity<AllRestaurantPerformances> allRestaurantPerformances(@RequestParam("start") Instant start, @RequestParam("end") Instant end)
    {
        Optional<AllRestaurantPerformances> allRestaurantPerformances=performanceService.allRestaurantPerformances(start,end);

        if(allRestaurantPerformances.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(allRestaurantPerformances.get());

    }
    
}
