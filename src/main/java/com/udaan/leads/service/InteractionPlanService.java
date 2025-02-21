package com.udaan.leads.service;

import com.udaan.leads.entity.interactions.InteractionPlan;
import com.udaan.leads.entity.restaurant.Restaurant;
import com.udaan.leads.repository.InteractionPlanRepository;
import com.udaan.leads.repository.LeadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InteractionPlanService {

    @Autowired
    InteractionPlanRepository interactionPlanRepository;

    @Autowired
    LeadRepository leadRepository;


    //Calculates the next call date for a restaurant based on its last call date and call frequency.
    //Done to account for DST
    private Instant calculateNextCallDate(String restaurantId, Instant lastCallDate) {

        Restaurant restaurant=leadRepository.findByRestaurantId(restaurantId);

        // Get the restaurant's time zone
        ZoneId restaurantZoneId = ZoneId.of(restaurant.getAddress().getTimeZone());

        // Convert the last call date to the restaurant's time zone
        ZonedDateTime zonedLastCallDate = lastCallDate.atZone(ZoneOffset.UTC).withZoneSameInstant(restaurantZoneId);

        // Calculate the next call date by adding 'callEveryNDay' days
        ZonedDateTime nextCallDate = zonedLastCallDate.plusDays(restaurant.getCallEveryNDay());

        // Convert the next call date back to UTC before returning and saving in MongoDB
        return nextCallDate.toInstant();
    }

    //Saves an interaction plan for a restaurant based on its last interaction.
    public void saveInteractionPlan(String restaurantId, Instant lastCallDate) {

        Instant nextCallDate = calculateNextCallDate(restaurantId, lastCallDate);
        Restaurant restaurant=leadRepository.findByRestaurantId(restaurantId); //must exist because a interaction is already created

        //(if exists) Getting the previously existing plan of the restaurant, to update it
        Optional<InteractionPlan> existingInteractionPlan=Optional.ofNullable(interactionPlanRepository.findByRestaurantId(restaurantId));

        //1st time planning
        if (existingInteractionPlan.isEmpty()) {
            InteractionPlan interactionPlan = new InteractionPlan();

            interactionPlan.setRestaurantId(restaurantId);
            interactionPlan.setNextCallDate(nextCallDate);
            interactionPlan.setLastCallMade(lastCallDate);
            interactionPlan.setRestaurantName(restaurant.getName());

            interactionPlanRepository.save(interactionPlan);
        }
        else //If earlier plan exists, update
        {
            log.info("Updating the call schedule");
            InteractionPlan interactionPlan = existingInteractionPlan.get();
            interactionPlan.setNextCallDate(nextCallDate);
            interactionPlan.setLastCallMade(lastCallDate);

            interactionPlanRepository.save(interactionPlan);
        }
    }

    public Optional<List<InteractionPlan>> getCallsScheduledForToday() {

        Instant startOfDay = Instant.now().truncatedTo(ChronoUnit.DAYS);        // 2024-12-29T00:00:00Z

        Instant endOfDay = startOfDay.plus(1, ChronoUnit.DAYS);    // 2024-12-30T00:00:00Z

        log.info("Start of Day: {}, End of Day: {}", startOfDay, endOfDay);

        //Find all calls scheduled for today
        return Optional.of(interactionPlanRepository.findByNextCallDate(startOfDay,endOfDay));
    }

    public Optional<InteractionPlan> lastCallMade(String leadId)
    {
        return Optional.ofNullable(interactionPlanRepository.findByRestaurantId(leadId));

    }



}


