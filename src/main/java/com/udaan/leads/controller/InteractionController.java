package com.udaan.leads.controller;

import com.udaan.leads.entity.interactions.InteractionHistory;
import com.udaan.leads.entity.interactions.InteractionPlan;
import com.udaan.leads.service.InteractionHistoryService;
import com.udaan.leads.service.InteractionPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/interactions")
public class InteractionController
{
    @Autowired
    InteractionHistoryService interactionHistoryService;

    @Autowired
    InteractionPlanService interactionPlanService;

    //Create Interaction

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<InteractionHistory> saveInteraction(@RequestBody InteractionHistory interactionHistory)
    {
        try {
            //Will also create a next CALL date, if InteractionType is of CALL type
            return ResponseEntity.status(HttpStatus.CREATED).body(interactionHistoryService.saveInteraction(interactionHistory));
        }
        catch (Exception e)
        {
            log.info("Error saving interaction {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //Get all calls scheduled for today

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/today-calls")
    public ResponseEntity<List<InteractionPlan>> getCallsScheduledForToday()
    {
        Optional<List<InteractionPlan>> callsScheduledForToday= interactionPlanService.getCallsScheduledForToday();

        if (callsScheduledForToday.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(callsScheduledForToday.get());
    }

    //Check when last call was made to the lead

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/last-call/{leadId}")
    public ResponseEntity<InteractionPlan> lastCallMade(@PathVariable("leadId") String leadId)
    {
        Optional<InteractionPlan> interactionPlan=interactionPlanService.lastCallMade(leadId);

        if (interactionPlan.isEmpty())
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.OK).body(interactionPlan.get());
    }
}
