package com.udaan.leads.entity.restaurant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.udaan.leads.entity.restaurant.address.Address;
import com.udaan.leads.entity.restaurant.contact.Contact;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "restaurants")
public class Restaurant
{

    @Id
    private String leadId;

    @Indexed(unique = true)
    private String restaurantId;
    private String name;
    private String cuisine;
    private Address address;            // contains timeZone
    private List<Contact> contacts;

    private LeadStatus leadStatus;

    private String currentManagerId;
    @JsonFormat
    private Instant managerSince;       // tracks when was the current manager allotted
    private String previousManagerId;   // this field is automatically updated if new KAM is assigned


    private int callEveryNDay;         // 1 for every day, 7 for weekly

}

