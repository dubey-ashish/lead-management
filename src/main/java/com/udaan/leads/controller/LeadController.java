package com.udaan.leads.controller;

import com.udaan.leads.entity.restaurant.LeadStatus;
import com.udaan.leads.entity.restaurant.Restaurant;
import com.udaan.leads.service.LeadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
@Slf4j
@RestController
@RequestMapping("/v1/lead")
public class LeadController
{
    @Autowired
    LeadService leadService;

    // Save a Lead

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Restaurant> saveLead(@RequestBody Restaurant restaurant) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(leadService.saveLead(restaurant));
        } catch (Exception e) {
            log.error("Error saving lead: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Get a Lead by id

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-lead/{leadId}")
    public ResponseEntity<Restaurant> getLeadById(@PathVariable("leadId") String leadId)
    {
        Optional<Restaurant> restaurant=leadService.getLeadById(leadId);

        if(restaurant.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404

        return ResponseEntity.status(HttpStatus.OK).body(restaurant.get()); //200
    }


    //Get all Leads

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-leads")
    public ResponseEntity<Iterable<Restaurant>> getAllLeads()
    {
        Optional<Iterable<Restaurant>> restaurants=leadService.getAllLeads();

        if(restaurants.isEmpty()) {
            log.info("No leads present");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404
        }

        return ResponseEntity.status(HttpStatus.OK).body(restaurants.get());  //200
    }


    // Get all leads for a specific KAM-Id

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-leads/{kamId}")
    public ResponseEntity<Iterable<Restaurant>> getLeadsByKAMId(@PathVariable("kamId") String kamId)
    {
        Optional<Iterable<Restaurant>> restaurants=leadService.getLeadsByKAMId(kamId);

        if(restaurants.isEmpty()) {
            log.info("Leads not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404
        }

        return ResponseEntity.status(HttpStatus.OK).body(restaurants.get());  //200
    }


    // Assign/Update KAM : records time of appointment and maintains a previous KAM id.

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/assign-kam")
    public ResponseEntity<Restaurant> assignKAM(@RequestParam("leadId") String leadId, @RequestParam("kamId") String kamId)
    {
        Optional<Restaurant> restaurant=leadService.assignKAM(leadId,kamId);
        if (restaurant.isEmpty())
        {
            log.info("No lead found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //404}
        }
        return ResponseEntity.status(HttpStatus.OK).body(restaurant.get()); //200
    }

    // Change status of a Lead

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/change-status")
    public ResponseEntity<Restaurant> changeStatus(@RequestParam("leadId") String leadId, @RequestParam("leadStatus") LeadStatus leadStatus)
    {
        Optional<Restaurant> restaurant=leadService.changeStatus(leadId,leadStatus);

        if (restaurant.isEmpty()) {
            log.info("Lead not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }//404

        return ResponseEntity.status(HttpStatus.OK).body(restaurant.get()); //200

    }
}
