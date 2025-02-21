package com.udaan.leads.entity.interactions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "interaction_plan")
public class InteractionPlan
{
    @Id
    private String planId;

    private String restaurantId;
    private String restaurantName;
    @JsonFormat
    private Instant lastCallMade;
    @JsonFormat
    private Instant nextCallDate;
}

