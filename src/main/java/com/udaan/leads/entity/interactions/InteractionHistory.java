package com.udaan.leads.entity.interactions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.udaan.leads.entity.restaurant.contact.ContactInfo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "interaction_history")
public class InteractionHistory {

    @Id
    private String interactionID;
    private String restaurantId;


    @Indexed(unique = true)
    private String thirdPartyService;       //Could be an external unique id for when the company outsources 'interaction' to some automated system/service.

    private String keyAccountManagerId;



    // Combination of {restaurantId,employeeId} is unique
    //'entity' -> 'restaurant' -> 'contact' ->employeeId
    // tracks which specific employee was interacted with
    private String employeeId;

    //specific Point of Contact (POC) (Eg a specific phone number among many) : 'entity' -> 'restaurant' -> 'contact' -> 'ContactInfo'
    //null if 'InteractionType' is 'MEETING'
    private ContactInfo contactInfo;

    private InteractionType interactionType;//if CALL then it will automatically create a new future interaction date based on call frequency with the restaurant
    @JsonFormat
    private Instant timeOfInteraction;      //not ZonedDateTime because Mongo anyway converts it to UTC
    private String details; // Notes
}