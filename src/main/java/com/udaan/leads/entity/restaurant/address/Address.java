package com.udaan.leads.entity.restaurant.address;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private Coordinates coordinates;
    private String timeZone;                // Eg Asia/Kolkata, Asia/Tokyo, America/Los_Angeles (Format: IANA Time Zone Naming Convention)
}
