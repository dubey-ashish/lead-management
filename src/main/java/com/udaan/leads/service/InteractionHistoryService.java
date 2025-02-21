package com.udaan.leads.service;

import com.udaan.leads.entity.interactions.InteractionHistory;
import com.udaan.leads.repository.InteractionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.udaan.leads.entity.interactions.InteractionType.CALL;

@Service
public class InteractionHistoryService {

    @Autowired
    InteractionHistoryRepository interactionHistoryRepository;

    @Autowired
    InteractionPlanService interactionPlanService;

    public InteractionHistory saveInteraction(InteractionHistory interactionHistory) {
        interactionHistoryRepository.save(interactionHistory);

        //Every time a CALL type of Interaction is made, a future call date is automatically created based on call frequency for the lead
        if(interactionHistory.getInteractionType()==CALL) {
            interactionPlanService.saveInteractionPlan(interactionHistory.getRestaurantId(), interactionHistory.getTimeOfInteraction());
        }

        return interactionHistory;
    }

}
