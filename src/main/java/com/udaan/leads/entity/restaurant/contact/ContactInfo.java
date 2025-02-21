package com.udaan.leads.entity.restaurant.contact;


import lombok.Data;

@Data
public class ContactInfo {
    private ContactType type;
    private String value;       //When 'type' is IN_PERSON the 'value' could be null/"At office"/"Conference Room" etc
}
